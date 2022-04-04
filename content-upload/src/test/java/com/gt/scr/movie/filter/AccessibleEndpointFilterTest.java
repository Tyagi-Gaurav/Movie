package com.gt.scr.movie.filter;

import com.gt.scr.movie.config.AccessibleEndpointConfig;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccessibleEndpointFilterTest {
    private final MockServerHttpResponse mockServerHttpResponse = new MockServerHttpResponse();
    private AccessibleEndpointFilter accessibleEndpointFilter;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private WebFilterChain webFilterChain;


    @BeforeEach
    void setUp() {
        AccessibleEndpointConfig toggleConfig =
                new AccessibleEndpointConfig(Map.of("GET-/allowedPath", true),
                        Map.of("GET-/allowedPath/.*/user", true,
                                "GET-/allowedPathWithNumber/(\\d)/user", true));
        accessibleEndpointFilter = new AccessibleEndpointFilter(toggleConfig);
    }

    @Test
    void shouldNotAllowEndpointToBeServicedIfItIsNotFoundInAccessibleEndpointList() {
        MockServerHttpRequest notAllowedMockRequest =
                MockServerHttpRequest.method(HttpMethod.GET, URI.create("/disallowedPath")).build();
        when(serverWebExchange.getRequest()).thenReturn(notAllowedMockRequest);
        when(serverWebExchange.getResponse()).thenReturn(mockServerHttpResponse);

        //when
        Mono<Void> result = accessibleEndpointFilter.filter(serverWebExchange, webFilterChain);

        //then
        StepVerifier.create(result).verifyComplete();
        assertThat(serverWebExchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldAllowEndpointToBeServicedIfItIsFoundInAccessibleEndpointList() {
        MockServerHttpRequest allowedMockRequest =
                MockServerHttpRequest.method(HttpMethod.GET, URI.create("/allowedPath")).build();
        when(serverWebExchange.getRequest()).thenReturn(allowedMockRequest);
        when(webFilterChain.filter(serverWebExchange)).thenReturn(Mono.empty());

        //when
        Mono<Void> result = accessibleEndpointFilter.filter(serverWebExchange, webFilterChain);

        //then
        StepVerifier.create(result).verifyComplete();
        verify(webFilterChain).filter(serverWebExchange);
    }

    @Test
    void shouldAllowEndpointToBeServicedIfItIsFoundInAccessibleEndpointRegexList() {
        MockServerHttpRequest allowedMockRequest =
                MockServerHttpRequest.method(HttpMethod.GET, URI.create("/allowedPath/id/user")).build();
        when(serverWebExchange.getRequest()).thenReturn(allowedMockRequest);
        when(webFilterChain.filter(serverWebExchange)).thenReturn(Mono.empty());

        //when
        Mono<Void> result = accessibleEndpointFilter.filter(serverWebExchange, webFilterChain);

        //then
        StepVerifier.create(result).verifyComplete();
        verify(webFilterChain).filter(serverWebExchange);
    }

    @Test
    void shouldNotAllowEndpointToBeServicedIfItIsFoundInAccessibleEndpointRegexList() {
        MockServerHttpRequest allowedMockRequest =
                MockServerHttpRequest.method(HttpMethod.GET, URI.create("/allowedPathWithNumber/id/user")).build();
        when(serverWebExchange.getRequest()).thenReturn(allowedMockRequest);
        when(serverWebExchange.getResponse()).thenReturn(mockServerHttpResponse);

        //when
        Mono<Void> result = accessibleEndpointFilter.filter(serverWebExchange, webFilterChain);

        //then
        StepVerifier.create(result).verifyComplete();
        assertThat(serverWebExchange.getResponse().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}