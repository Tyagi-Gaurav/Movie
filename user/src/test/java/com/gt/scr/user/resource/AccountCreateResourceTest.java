package com.gt.scr.user.resource;

import com.gt.scr.spc.domain.User;
import com.gt.scr.exception.UnauthorizedException;
import com.gt.scr.user.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountCreateResourceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AccountCreateResource accountCreateResource;

    @BeforeEach
    void setUp() {
        accountCreateResource = new AccountCreateResource(userService, passwordEncoder);
    }

    @Test
    void shouldCreateAccountForNormalUser() {
        AccountCreateRequestDTO accountCreateRequestDTO =
                new AccountCreateRequestDTO("userName",
                        "password",
                        "firstName",
                        "lastName",
                        "USER");
        when(passwordEncoder.encode("password")).thenReturn("password");
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
}