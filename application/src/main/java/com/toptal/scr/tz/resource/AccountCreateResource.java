package com.toptal.scr.tz.resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountCreateResource {

    @PostMapping(path = "/account/create",
            consumes = {"application/vnd+account.create.v1+json"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> createAccount() {
        return ResponseEntity.noContent().build();
    }
}
