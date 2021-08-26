package com.gt.scr.movie.resource.domain;

import com.gt.scr.movie.service.domain.Role;

import java.util.UUID;

public record UserProfile(UUID id, String authority) {
    boolean isUser() {
        return Role.USER.toString().equals(authority());
    }
}
