package com.toptal.scr.tz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

@Configuration
public class ApplicationConfiguration {
    @Bean
    @ConfigurationProperties("database")
    public DatabaseConfig databaseConfig() {
        return ModifiableDatabaseConfig.create();
    }

    @Bean
    @ConfigurationProperties("auth")
    public AuthConfig authConfig() {
        return ModifiableAuthConfig.create();
    }

    @Bean
    @ConfigurationProperties("validation")
    public ValidationConfig validationConfig() {
        return ModifiableValidationConfig.create();
    }

    @Bean
    @Qualifier("signingKey")
    public Key signingKey(AuthConfig authConfig) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(authConfig.secret());
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(DatabaseConfig databaseConfig) {
        return new LettuceConnectionFactory(
                databaseConfig.host(),
                databaseConfig.port());
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }
}
