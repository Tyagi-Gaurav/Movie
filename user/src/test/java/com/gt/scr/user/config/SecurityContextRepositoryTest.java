package com.gt.scr.user.config;

import com.gt.scr.domain.User;
import com.gt.scr.user.util.UserBuilder;
import com.gt.scr.util.JwtTokenUtil;
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
        securityContextRepository = new SecurityContextRepository(authenticationManager);
    }

    @Test
    void saveShouldThrowUnSupportedException() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> securityContextRepository.save(serverWebExchange, securityContext));
    }

    @Test
    void loadShouldLoadUserIntoSecurityContext() {
        String principle = "Principle";
        String KEY_256_BIT = "8A6872AD13BEC411DAC9746C7FEDB8A6872AD13BEC411DAC9746C7FEDB";
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY_256_BIT);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        when(serverWebExchange.getRequest()).thenReturn(serverHttpRequest);
        User user = UserBuilder.aUser().build();
        String token = JwtTokenUtil.generateToken(user.getUsername(),
                user.id(),
                user.authorities(),
                Duration.ofMinutes(1L), signingKey);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);

        when(serverHttpRequest.getHeaders()).thenReturn(httpHeaders);

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