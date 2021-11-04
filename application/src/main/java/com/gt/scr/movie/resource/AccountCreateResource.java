package com.gt.scr.movie.resource;

import com.gt.scr.movie.exception.UnauthorizedException;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.Role;
import com.gt.scr.movie.service.domain.User;
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

    public AccountCreateResource(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/user/account/create",
            consumes = {"application/vnd+account.create.v1+json"},
            produces = {"application/vnd+account.create.v1+json"})
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> createAccount(@Valid @RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {

        if (Role.ADMIN.toString().equals(accountCreateRequestDTO.role())) {
            return Mono.error(new UnauthorizedException());
        }

        return userService.add(new User(UUID.randomUUID(),
                accountCreateRequestDTO.firstName(),
                accountCreateRequestDTO.lastName(),
                accountCreateRequestDTO.userName(),
                passwordEncoder.encode(accountCreateRequestDTO.password()),
                Collections.singletonList(new SimpleGrantedAuthority(accountCreateRequestDTO.role()))));
    }
}
