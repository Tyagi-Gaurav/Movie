package com.gt.scr.movie.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("user-app")
public record UserAppConfig(String host, int port, int mgtPort) {}
