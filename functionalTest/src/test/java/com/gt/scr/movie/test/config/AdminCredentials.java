package com.gt.scr.movie.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "admin-credentials")
public record AdminCredentials(String userName, String password) { }
