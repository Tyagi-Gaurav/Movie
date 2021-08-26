package com.gt.scr.movie.resource;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.service.UserService;
import com.gt.scr.movie.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.UUID;

@RestController
public class AccountCreateResource {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/user/account/create",
            consumes = {"application/vnd+account.create.v1+json"},
            produces = {"application/vnd+account.create.v1+json"})
    public ResponseEntity<Void> createAccount(@Valid @RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {
        User user = new User(UUID.randomUUID(),
                accountCreateRequestDTO.firstName(),
                accountCreateRequestDTO.lastName(),
                accountCreateRequestDTO.userName(),
                passwordEncoder.encode(accountCreateRequestDTO.password()),
                Collections.singletonList(new SimpleGrantedAuthority(accountCreateRequestDTO.role())));

        userService.add(user);

        return ResponseEntity.noContent().build();
    }
}
