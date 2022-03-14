package com.gt.scr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Collection;
import java.util.List;
import java.util.Set;
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

    public User(UUID id, String firstName, String lastName, String username, String password, List<String> roles) {
        this(id, firstName, lastName, username, password, null, null, null, roles);
    }

    public String getRole() {
        return authorities().stream().findFirst().orElseGet(() -> "");
    }
}
