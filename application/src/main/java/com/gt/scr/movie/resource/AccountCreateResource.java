package com.gt.scr.movie.resource;

import com.gt.scr.movie.exception.UnauthorizedException;
import com.gt.scr.movie.ext.user.CreateAccountClient;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.Role;
import com.gt.scr.movie.service.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Collections;
import java.util.UUID;

@RestController
public class AccountCreateResource {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CreateAccountClient createAccountClient;

    private static final Logger LOG = LoggerFactory.getLogger(AccountCreateResource.class);

    public AccountCreateResource(UserService userService,
                                 PasswordEncoder passwordEncoder,
                                 CreateAccountClient createAccountClient) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.createAccountClient = createAccountClient;
    }

    @PostMapping(path = "/user/account/create",
            consumes = {"application/vnd+account.create.v1+json"},
            produces = {"application/vnd+account.create.v1+json"})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> createAccount(@Valid @RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {

        if (Role.ADMIN.toString().equals(accountCreateRequestDTO.role())) {
            return Mono.error(new UnauthorizedException());
        }

        Mono<Void> userAccountCreateInUserApp = Mono.empty();
        try {
            LOG.info("Preparing to create account on User application");
            userAccountCreateInUserApp = createAccountClient.createAccount(accountCreateRequestDTO);
            LOG.info("Account call made.");
        } catch(Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Account create call errored on User.");
                LOG.error(e.getMessage(), e);
            }
        }

        return userAccountCreateInUserApp.then(userService.add(new User(UUID.randomUUID(),
                accountCreateRequestDTO.firstName(),
                accountCreateRequestDTO.lastName(),
                accountCreateRequestDTO.userName(),
                passwordEncoder.encode(accountCreateRequestDTO.password()),
                Collections.singletonList(new SimpleGrantedAuthority(accountCreateRequestDTO.role())))));
    }
}
