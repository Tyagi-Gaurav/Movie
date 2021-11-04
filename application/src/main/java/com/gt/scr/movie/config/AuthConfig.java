package com.gt.scr.movie.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@ConstructorBinding
@ConfigurationProperties("auth")
public record AuthConfig(Duration tokenDuration, String secret) { }
