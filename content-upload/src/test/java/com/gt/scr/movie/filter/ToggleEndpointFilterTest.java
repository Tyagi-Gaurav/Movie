package com.gt.scr.movie.filter;

import com.gt.scr.movie.config.ToggleConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ToggleEndpointFilterTest {
    MockServerHttpResponse mockServerHttpResponse = new MockServerHttpResponse();
    private ToggleEndpointFilter toggleEndpointFilter;

    @Mock
    private ServerWebExchange notAllowedServerExchange;

    @Mock
    private ServerWebExchange allowedServerExchange;

    @Mock
    private WebFilterChain webFilterChain;


    @BeforeEach
    void setUp() {
        ToggleConfig toggleConfig =
                new ToggleConfig(Set.of("GET-/disallowedPath"));
        toggleEndpointFilter = new ToggleEndpointFilter(toggleConfig);
        when(notAllowedServerExchange.getResponse()).thenReturn(mockServerHttpResponse);
    }

    @Test
    void shouldNotAllowEndpointToBeServicedIfItIsFoundInToggleEndpointList() {
        MockServerHttpRequest notAllowedMockRequest =
                MockServerHttpRequest.method(HttpMethod.GET, URI.create("/disallowedPath")).build();
        when(notAllowedServerExchange.getRequest()).thenReturn(notAllowedMockRequest);

        //when
        Mono<Void> result = toggleEndpointFilter.filter(notAllowedServerExchange, webFilterChain);

        //then
        StepVerifier.create(result).verifyComplete();
        assertThat(notAllowedServerExchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldAllowEndpointToBeServicedIfItIsNotFoundInToggleEndpointList() {
        MockServerHttpRequest allowedMockRequest =
                MockServerHttpRequest.method(HttpMethod.GET, URI.create("/allowedPath")).build();
        when(allowedServerExchange.getRequest()).thenReturn(allowedMockRequest);
        mockServerHttpResponse.setStatusCode(HttpStatus.OK);
        when(webFilterChain.filter(allowedServerExchange)).thenReturn(Mono.empty());

        //when
        Mono<Void> result = toggleEndpointFilter.filter(allowedServerExchange, webFilterChain);

        //then
        StepVerifier.create(result).verifyComplete();
        assertThat(notAllowedServerExchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}