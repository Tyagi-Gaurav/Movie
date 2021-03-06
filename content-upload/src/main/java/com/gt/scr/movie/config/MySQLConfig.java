package com.gt.scr.movie.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("mysql")
public record MySQLConfig(String driver,
                          String host,
                          int port,
                          String database,
                          String schema,
                          String user,
                          String password,
                          int minPoolSize,
                          int maxPoolSize) {}
