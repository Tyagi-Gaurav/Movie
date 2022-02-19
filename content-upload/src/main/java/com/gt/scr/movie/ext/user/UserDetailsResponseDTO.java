package com.gt.scr.movie.ext.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gt.scr.domain.User;

import java.util.Collections;
import java.util.UUID;

@JsonSerialize
@JsonDeserialize
public record UserDetailsResponseDTO(String userName,
                                     String password,
                                     String firstName,
                                     String lastName,
                                     String role,
                                     UUID id) {
    
    public User toUser() {
        return new User(id(), firstName(), lastName(), userName(), password(),
                Collections.singletonList(role()));
    }
}
