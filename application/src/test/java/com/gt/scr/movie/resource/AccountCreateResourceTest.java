package com.gt.scr.movie.resource;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void shouldCreateAccount() {
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
}