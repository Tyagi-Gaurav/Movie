package com.toptal.scr.tz.config;

import org.immutables.value.Value;

@Value.Modifiable
public interface ValidationConfig {
    int maxUserNameLength();

    int minUserNameLength();

    int maxPasswordLength();

    int minPasswordLength();
}
