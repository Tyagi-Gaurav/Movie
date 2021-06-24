package com.gt.scr.movie.config;

import org.immutables.value.Value;

import java.time.Duration;

@Value.Modifiable
public interface AuthConfig {
    Duration tokenDuration();

    String secret();
}
