package com.gt.scr.movie.resource;

import com.gt.scr.exception.ApplicationAuthenticationException;
import com.gt.scr.spc.domain.User;
import com.gt.scr.movie.config.AuthConfig;
import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import com.gt.scr.movie.service.UserService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginResourceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    private final AuthConfig authConfig = new AuthConfig(Duration.ofMinutes(1L), "secret");
    private final static String KEY_256_BIT = "8A6872AD13BEC411DAC9746C7FEDB8A6872AD13BEC411DAC9746C7FEDB";

    private LoginResource loginResource;

    @BeforeEach
    void setUp() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY_256_BIT);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        loginResource = new LoginResource(passwordEncoder, userService, authConfig, signingKey);
    }

    @Test
    void shouldReturnUserWhenValidLoginDetailsProvided() {
        String validTestUserName = "validTestUserName";
        String validTestUserPassword = "validTestUserPassword";

        when(passwordEncoder.matches(eq(validTestUserPassword), anyString())).thenReturn(true);
        when(userService.findByUsername(validTestUserName)).thenReturn(Mono.just(new User(UUID.randomUUID(),
                "fn", "ln", validTestUserName, validTestUserPassword, Collections.emptyList())));

        Mono<LoginResponseDTO> login =
                loginResource.login(new LoginRequestDTO(
                        validTestUserName,
                        validTestUserPassword));

        StepVerifier.create(login)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldThrowCorrectErrorWhenLoginDetailsAreInvalid() {
        String testUserName = "validTestUserName";
        String password = "password";

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        when(userService.findByUsername(testUserName)).thenReturn(Mono.just(new User(UUID.randomUUID(),
                "fn", "ln", testUserName, password, Collections.emptyList())));

        Mono<LoginResponseDTO> login =
                loginResource.login(new LoginRequestDTO(
                        testUserName,
                        password));

        StepVerifier.create(login)
                .expectError(ApplicationAuthenticationException.class)
                .verify();
    }

    @Test
    void shouldThrowCorrectErrorWhenUserNotFound() {
        String testUserName = "validTestUserName";
        String password = "password";

        when(userService.findByUsername(testUserName)).thenReturn(Mono.empty());

        Mono<LoginResponseDTO> login =
                loginResource.login(new LoginRequestDTO(
                        testUserName,
                        password));

        StepVerifier.create(login)
                .expectError(ApplicationAuthenticationException.class)
                .verify();
    }

    @Test
    void shouldThrowCorrectErrorWhenUserNotFoundExceptionIsReturned() {
        String testUserName = "validTestUserName";
        String password = "password";

        when(userService.findByUsername(testUserName))
                .thenReturn(Mono.error(new UsernameNotFoundException("User Not Found")));

        Mono<LoginResponseDTO> login =
                loginResource.login(new LoginRequestDTO(
                        testUserName,
                        password));

        StepVerifier.create(login)
                .expectError(ApplicationAuthenticationException.class)
                .verify();
    }
}