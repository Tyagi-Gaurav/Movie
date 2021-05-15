package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.AccountCreateRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountCreateResource {

    @PostMapping(path = "/account/create",
            consumes = {"application/vnd+account.create.v1+json"},
            produces = {"application/vnd+account.create.v1+json"})
    public ResponseEntity<Void> createAccount(@RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {
        return ResponseEntity.noContent().build();
    }
}
