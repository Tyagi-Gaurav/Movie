package com.gt.scr.movie.config;

import org.immutables.value.Value;

@Value.Modifiable
public interface MySQLConfig {
    String driver();

    String host();

    int port();

    String database();

    String user();

    String password();

    int minPoolSize();

    int maxPoolSize();
}