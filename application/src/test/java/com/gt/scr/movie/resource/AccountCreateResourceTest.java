package com.gt.scr.movie.resource;

import com.gt.scr.movie.exception.UnauthorizedException;
import com.gt.scr.movie.ext.user.CreateAccountClient;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountCreateResourceTest {

    private AccountCreateResource accountCreateResource;

    @Mock
    private CreateAccountClient createAccountClient;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        accountCreateResource = new AccountCreateResource(userService, passwordEncoder, createAccountClient);
    }

    @Test
    void shouldCreateAccountForNormalUser() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                new AccountCreateRequestDTO("userName",
                        "password",
                        "firstName",
                        "lastName",
                        "USER");

        when(createAccountClient.createAccount(accountCreateRequestDTO)).thenReturn(Mono.empty());
        when(passwordEncoder.encode("password")).thenReturn("password");
        when(userService.add(any())).thenReturn(Mono.empty());

        Mono<Void> account = accountCreateResource.createAccount(accountCreateRequestDTO);

        StepVerifier.create(account).verifyComplete();
        verify(createAccountClient).createAccount(accountCreateRequestDTO);
        verify(userService).add(any(User.class));
    }

    @Test
    void shouldFailForCreateAccountOfAdminUser() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                new AccountCreateRequestDTO("userName",
                        "password",
                        "firstName",
                        "lastName",
                        "ADMIN");

        Mono<Void> account = accountCreateResource.createAccount(accountCreateRequestDTO);

        StepVerifier.create(account)
                .expectError(UnauthorizedException.class)
                .verify();
        verifyNoInteractions(createAccountClient);
        verifyNoInteractions(userService);
    }

    @Test
    void shouldNotFailForCreateAccountWhenUserCallFails() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                new AccountCreateRequestDTO("userName",
                        "password",
                        "firstName",
                        "lastName",
                        "USER");

        when(createAccountClient.createAccount(accountCreateRequestDTO)).thenThrow(new RuntimeException());
        when(userService.add(any())).thenReturn(Mono.empty());

        Mono<Void> account = accountCreateResource.createAccount(accountCreateRequestDTO);

        StepVerifier.create(account).verifyComplete();

        verify(userService).add(any(User.class));
    }
}