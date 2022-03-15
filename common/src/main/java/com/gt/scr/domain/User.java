package com.gt.scr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Collection;
import java.util.UUID;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
public record User(UUID id,
                   String firstName,
                   String lastName,
                   String username,
                   String password,
                   String dateOfBirth,
                   Gender gender,
                   String homeCountry,
                   Collection<String> authorities) {

    public String getRole() {
        return authorities().stream().findFirst().orElseGet(() -> "");
    }
}
