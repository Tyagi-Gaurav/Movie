package com.gt.scr.movie.config;

import com.gt.scr.domain.Gender;
import com.gt.scr.domain.User;
import com.gt.scr.movie.service.FetchUserService;
import com.gt.scr.utils.JwtTokenUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@ExtendWith(MockitoExtension.class)
class AuthenticationManagerTest {
    @Mock
    private FetchUserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Key signingKey;
    private AuthenticationManager authenticationManager;
    private User user = new User(UUID.randomUUID(),
            "TestFirstName",
            "TestLastName",
            "TestUserName",
            "",
            "01/01/1989",
            Gender.FEMALE,
            "GBR",
            Collections.singletonList("ADMIN"));
    private final static String KEY_256_BIT = "8A6872AD13BEC411DAC9746C7FEDB8A6872AD13BEC411DAC9746C7FEDB";

    @BeforeEach
    void setUp() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY_256_BIT);
        signingKey =  new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        authenticationManager = new AuthenticationManager(userService, signingKey);
    }

    @Test
    void shouldReturnEmptyWhenNoUserFound() {
        String token = JwtTokenUtil.generateTokenV2(user, Duration.ofMinutes(1L), signingKey);;

        when(userService.findUserBy(user.id())).thenReturn(Mono.empty());

        //when
        Mono<Authentication> authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, ""));

        StepVerifier.create(authenticate)
                .expectError(IllegalCallerException.class)
                .verify();
    }

    @Test
    void shouldReturnEmptyWhenTokenIsExpired() throws InterruptedException {
        String token = JwtTokenUtil.generateTokenV2(user, Duration.ofMillis(1L), signingKey);;

        await("Wait for token to Expire").atLeast(Duration.ofMillis(50))
                .until(() -> true);

        //when
        Mono<Authentication> authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, ""));

        StepVerifier.create(authenticate)
                .expectNextCount(0)
                .verifyComplete();

        verifyNoInteractions(userService);
    }

    @Test
    void shouldReturnResponseWhenUserFound() {
        String token = JwtTokenUtil.generateTokenV2(user, Duration.ofMinutes(1L), signingKey);;

        when(userService.findUserBy(user.id())).thenReturn(Mono.just(user));

        //when
        Mono<Authentication> authenticate =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, ""));

        StepVerifier.create(authenticate)
                .expectNextCount(1)
                .verifyComplete();
    }
}