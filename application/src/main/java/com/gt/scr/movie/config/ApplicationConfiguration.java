package com.gt.scr.movie.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.movie.filter.LoggingInterceptor;
import com.gt.scr.movie.filter.MetricsInterceptor;
import com.gt.scr.movie.filter.RequestIdInterceptor;
import io.jsonwebtoken.SignatureAlgorithm;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redis.embedded.RedisServer;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer  {
    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Autowired
    private RequestIdInterceptor requestIdInterceptor;

    @Autowired
    private MetricsInterceptor metricsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestIdInterceptor);
        registry.addInterceptor(loggingInterceptor);
        registry.addInterceptor(metricsInterceptor);
    }

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
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setPort(databaseConfig.port());
        redisStandaloneConfiguration.setHostName(databaseConfig.host());

        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .clientOptions(
                        ClientOptions.builder()
                                .socketOptions(
                                        SocketOptions.builder()
                                                .connectTimeout(databaseConfig.connectionTimeout())
                                                .build())
                                .build())
                .commandTimeout(databaseConfig.commandTimeout())
                .build();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile("!prod")
    public RedisServer redisServer(DatabaseConfig databaseConfig) {
        return new RedisServer(databaseConfig.port());
    }
}
