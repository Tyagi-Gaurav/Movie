package com.toptal.scr.tz.resource.domain;

import com.toptal.scr.tz.service.domain.Role;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public interface UserProfile {
    UUID id();

    String authority();

    default boolean isUser() {
        return Role.USER.toString().equals(authority());
    }
}
