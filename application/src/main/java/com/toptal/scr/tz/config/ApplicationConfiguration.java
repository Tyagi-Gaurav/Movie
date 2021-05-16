package com.toptal.scr.tz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class ApplicationConfiguration {
    @Bean
    @ConfigurationProperties("database")
    public DatabaseConfig databaseConfig() {
        return ModifiableDatabaseConfig.create();
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
