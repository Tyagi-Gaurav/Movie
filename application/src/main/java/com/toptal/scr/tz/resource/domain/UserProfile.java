package com.toptal.scr.tz.resource.domain;

import org.immutables.value.Value;

@Value.Immutable
public interface UserProfile {
    String userName();

    String authority();
}
