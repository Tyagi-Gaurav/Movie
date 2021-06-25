package com.gt.scr.movie.test.config;

import org.immutables.value.Value;

@Value.Modifiable
public interface MovieAppConfig {
    String host();

    int port();
}
