package com.toptal.scr.tz.service.domain;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public interface UserTimezone {
    UUID id();

    String name();

    String city();

    int gmtOffset();
}
