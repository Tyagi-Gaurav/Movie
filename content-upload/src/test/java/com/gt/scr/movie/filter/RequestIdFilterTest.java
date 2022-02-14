package com.gt.scr.movie.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestIdFilterTest {

    private final RequestIdFilter requestIdFilter = new RequestIdFilter();

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private WebFilterChain webFilterChain;

    @Mock
    private ServerHttpRequest serverHttpRequest;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @Mock
    private HttpHeaders httpHeaders;

    @BeforeEach
    void setUp() {
        when(serverWebExchange.getRequest()).thenReturn(serverHttpRequest);
        when(serverWebExchange.getResponse()).thenReturn(serverHttpResponse);
        when(serverHttpResponse.getHeaders()).thenReturn(httpHeaders);
        when(serverHttpRequest.getHeaders()).thenReturn(httpHeaders);
    }

    @Test
    void shouldGenerateRequestIdWhenNoIdPresentInRequest() {
        //when
        requestIdFilter.filter(serverWebExchange, webFilterChain);

        //then
        ArgumentCaptor<String> uuidArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(httpHeaders).add(eq("requestId"), uuidArgumentCaptor.capture());

        String value = uuidArgumentCaptor.getValue();
        assertThat(UUID.fromString(value).toString()).hasToString(value);
    }

    @Test
    void shouldUseRequestIdWhenIdPassedInRequest() {
        //given
        String requestId = "requestIdPassedByClient";
        when(httpHeaders.getFirst("requestId")).thenReturn(requestId);

        //when
        requestIdFilter.filter(serverWebExchange, webFilterChain);

        //then
        ArgumentCaptor<String> uuidArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(httpHeaders).add(eq("requestId"), uuidArgumentCaptor.capture());

        String value = uuidArgumentCaptor.getValue();
        assertThat(value).isEqualTo(requestId);
    }
}