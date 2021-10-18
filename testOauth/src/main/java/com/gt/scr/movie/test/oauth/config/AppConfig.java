package com.gt.scr.movie.test.oauth.config;

import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(OAuth2AuthorizationServerConfiguration.class)
public class AppConfig {
}
