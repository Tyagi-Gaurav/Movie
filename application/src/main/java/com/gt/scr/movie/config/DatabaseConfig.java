package com.gt.scr.movie.config;

import org.immutables.value.Value;

import java.time.Duration;

@Value.Modifiable
public interface DatabaseConfig {
    String host();

    int port();

    Duration duplicateInterval();

    Duration connectionTimeout();

    Duration commandTimeout();
}
