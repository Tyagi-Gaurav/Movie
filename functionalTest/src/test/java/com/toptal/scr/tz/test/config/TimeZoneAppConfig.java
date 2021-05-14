package com.toptal.scr.tz.test.config;

import org.immutables.value.Value;

@Value.Modifiable
public interface TimeZoneAppConfig {
    String host();

    int port();
}
