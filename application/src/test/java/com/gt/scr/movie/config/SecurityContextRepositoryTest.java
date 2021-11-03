package com.gt.scr.movie.config;

import com.gt.scr.movie.filter.JwtTokenUtil;
import com.gt.scr.movie.util.UserBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityContextRepositoryTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ServerWebExchange serverWebExchange;

    @Mock
    private ServerHttpRequest serverHttpRequest;

    @Mock
    private SecurityContext securityContext;

    private SecurityContextRepository securityContextRepository;

    @BeforeEach
    void setUp() {
        String KEY_256_BIT = "8A6872AD13BEC411DAC9746C7FEDB8A6872AD13BEC411DAC9746C7FEDB";
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY_256_BIT);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        securityContextRepository = new SecurityContextRepository(authenticationManager);
        when(serverWebExchange.getRequest()).thenReturn(serverHttpRequest);
        String token = JwtTokenUtil.generateToken(UserBuilder.aUser().build(), Duration.ofMinutes(1L), signingKey);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);

        when(serverHttpRequest.getHeaders()).thenReturn(httpHeaders);
    }

    @Test
    void saveShouldThrowUnSupportedException() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> securityContextRepository.save(serverWebExchange, securityContext));
    }

    @Test
    void loadShouldLoadUserIntoSecurityContext() {
        String principle = "Principle";
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(principle, principle);
        Mono<Authentication> authenticationMono = Mono.just(usernamePasswordAuthenticationToken);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticationMono);

        //when
        Mono<SecurityContext> securityContextMono = securityContextRepository.load(serverWebExchange);

        StepVerifier.create(securityContextMono)
                .expectNext(new SecurityContextImpl(usernamePasswordAuthenticationToken))
                .verifyComplete();
    }
}