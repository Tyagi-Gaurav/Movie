package com.toptal.scr.tz.resource.domain;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public interface UserProfile {
    UUID id();

    String authority();
}
