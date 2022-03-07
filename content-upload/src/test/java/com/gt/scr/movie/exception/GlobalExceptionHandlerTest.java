package com.gt.scr.movie.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler validationExceptionHandler = new GlobalExceptionHandler();

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse httpResponse;

    @Test
    void onExceptionMarkResponseAsComplete() {
        when(serverWebExchange.getResponse()).thenReturn(httpResponse);
        when(httpResponse.setComplete()).thenReturn(Mono.empty());

        Mono<Void> handle = validationExceptionHandler.handle(serverWebExchange, new IllegalArgumentException());

        StepVerifier.create(handle).verifyComplete();
        verify(httpResponse).setComplete();
    }
}