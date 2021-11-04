package com.gt.scr.movie.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationEntryPointTest {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpResponse serverHttpResponse;

    @Test
    void shouldReturnErrorWhenAuthenticationCommenced() {
        BadCredentialsException bad_credentials = new BadCredentialsException("Bad Credentials");
        when(serverWebExchange.getResponse()).thenReturn(serverHttpResponse);
        when(serverHttpResponse.writeWith(any())).thenReturn(Mono.error(bad_credentials));

        Mono<Void> badCredentials =
                jwtAuthenticationEntryPoint.commence(serverWebExchange, bad_credentials);

        StepVerifier.create(badCredentials)
                .expectError(BadCredentialsException.class)
                .verify();

        verify(serverHttpResponse).setStatusCode(HttpStatus.UNAUTHORIZED);
    }
}