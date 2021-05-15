package com.toptal.scr.tz.service.domain;

import org.immutables.value.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

@Value.Immutable
public interface User extends UserDetails {
    UUID id();

    String firstName();

    String lastName();

    List<String> authorityNames();
}
