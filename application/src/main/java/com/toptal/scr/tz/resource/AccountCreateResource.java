package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.AccountCreateRequestDTO;
import com.toptal.scr.tz.service.UserService;
import com.toptal.scr.tz.service.domain.ImmutableUser;
import com.toptal.scr.tz.service.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.UUID;

@RestController
public class AccountCreateResource {

    private static final Logger LOG = LoggerFactory.getLogger(AccountCreateResource.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/account/create",
            consumes = {"application/vnd+account.create.v1+json"},
            produces = {"application/vnd+account.create.v1+json"})
    public ResponseEntity<Void> createAccount(@RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {
        User user = ImmutableUser.builder()
                .id(UUID.randomUUID())
                .username(accountCreateRequestDTO.userName())
                .password(passwordEncoder.encode(accountCreateRequestDTO.password()))
                .firstName(accountCreateRequestDTO.firstName())
                .lastName(accountCreateRequestDTO.lastName())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(accountCreateRequestDTO.role())))
                .build();

        LOG.info("Adding User: " + user);

        userService.add(user);
        return ResponseEntity.noContent().build();
    }
}
