package com.gt.scr.movie.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("api-gateway")
public record ApiGatewayConfig(String host, int port, String contextPath) {}
