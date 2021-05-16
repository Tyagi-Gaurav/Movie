package com.toptal.scr.tz.config;

import org.immutables.value.Value;

@Value.Modifiable
public interface DatabaseConfig {
    String host();

    int port();
}
