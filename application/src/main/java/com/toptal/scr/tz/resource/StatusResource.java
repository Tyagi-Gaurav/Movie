package com.toptal.scr.tz.resource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusResource {

    @GetMapping(path = "/status",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("OK");
    }
}
