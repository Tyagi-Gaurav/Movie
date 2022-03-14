package com.gt.scr.user.resource.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gt.scr.domain.Gender;

import java.util.UUID;

@JsonSerialize
@JsonDeserialize
public record UserDetailsResponseDTO(String userName,
                                     String password,
                                     String firstName,
                                     String lastName,
                                     String role,
                                     UUID id,
                                     String dateOfBirth,
                                     Gender gender,
                                     String homeCountry) {


    public UserDetailsResponseDTO(String username, String password, String firstName, String lastName, String role, UUID id) {
        this(username, password, firstName, lastName, role, id, null, null, null);
    }
}
