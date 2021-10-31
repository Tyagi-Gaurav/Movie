package com.gt.scr.movie.config;

import com.gt.scr.movie.filter.JwtTokenUtil;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.User;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthenticationManagerTest {
    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Key signingKey;
    private AuthenticationManager authenticationManager;
    private User user = new User(UUID.randomUUID(),
            "TestFirstName",
            "TestLastName",
            "TestUserName",
            "",
            Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
    private final static String KEY_256_BIT = "8A6872AD13BEC411DAC9746C7FEDB8A6872AD13BEC411DAC9746C7FEDB";

    @BeforeEach
    void setUp() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY_256_BIT);
        signingKey =  new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        authenticationManager = new AuthenticationManager(userService, signingKey);
    }

    @Test
    void shouldReturnEmptyWhenNoUserFound() {
        String token = JwtTokenUtil.generateToken(user, Duration.ofMinutes(1L), signingKey);;

        when(userService.findUserBy(user.id())).thenReturn(Mono.empty());

        //when
        Mono<Authentication> authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, ""));

        StepVerifier.create(authenticate)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldReturnResponseWhenUserFound() {
        String token = JwtTokenUtil.generateToken(user, Duration.ofMinutes(1L), signingKey);;

        when(userService.findUserBy(user.id())).thenReturn(Mono.just(user));

        //when
        Mono<Authentication> authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, ""));

        StepVerifier.create(authenticate)
                .expectNextCount(1)
                .verifyComplete();
    }
}