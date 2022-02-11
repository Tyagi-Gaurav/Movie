package com.gt.scr.movie.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("movie-app")
public record MovieAppConfig(String host, int port, String path, int mgtPort) {}
