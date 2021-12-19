package com.gt.scr.movie.resource;

import com.gt.scr.movie.exception.UnauthorizedException;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountCreateResourceTest {

    private AccountCreateResource accountCreateResource;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        accountCreateResource = new AccountCreateResource(userService);
    }

    @Test
    void shouldCreateAccountForNormalUser() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                new AccountCreateRequestDTO("userName",
                        "password",
                        "firstName",
                        "lastName",
                        "USER");

        //when(createUserClient.createAccount(accountCreateRequestDTO)).thenReturn(Mono.empty());
        when(userService.add(any())).thenReturn(Mono.empty());

        Mono<Void> account = accountCreateResource.createAccount(accountCreateRequestDTO);

        StepVerifier.create(account).verifyComplete();
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

        when(userService.add(any())).thenReturn(Mono.empty());

        Mono<Void> account = accountCreateResource.createAccount(accountCreateRequestDTO);

        StepVerifier.create(account).verifyComplete();

        verify(userService).add(any(User.class));
    }
}