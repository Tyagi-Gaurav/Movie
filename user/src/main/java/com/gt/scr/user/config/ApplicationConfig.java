package com.gt.scr.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.scr.exception.ErrorResponseHelper;
import com.gt.scr.metrics.EndpointHistogram;
import com.gt.scr.metrics.EndpointRequestCounter;
import com.gt.scr.metrics.ExceptionCounter;
import com.gt.scr.user.filter.LoggingFilter;
import com.gt.scr.user.filter.MetricsInterceptor;
import com.gt.scr.user.filter.RequestIdFilter;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import io.jsonwebtoken.SignatureAlgorithm;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.config.EnableWebFlux;

import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;
import java.beans.PropertyVetoException;
import java.security.Key;

@Configuration
@EnableWebFlux
@ConfigurationPropertiesScan("com.gt.scr.user.config")
public class ApplicationConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private LoggingFilter loggingFilter;

    @Autowired
    private RequestIdFilter requestIdFilter;

    @Autowired
    private MetricsInterceptor metricsInterceptor;

    @Bean
    public WebFluxProperties webFluxProperties(){
        return new WebFluxProperties();
    }

    @Bean
    public ExceptionCounter exceptionCounter(MeterRegistry meterRegistry) {
        return new ExceptionCounter(meterRegistry);
    }

    @Bean
    public EndpointRequestCounter endpointRequestCounter(MeterRegistry meterRegistry) {
        return new EndpointRequestCounter(meterRegistry);
    }

    @Bean
    public EndpointHistogram endpointHistogram(MeterRegistry meterRegistry) {
        return new EndpointHistogram(meterRegistry);
    }

    @Bean
    public ErrorResponseHelper errorResponseHelper(ExceptionCounter exceptionCounter) {
        return new ErrorResponseHelper(exceptionCounter);
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

    @Primary
    @Bean
    public DataSource dataSource(MySQLConfig mySQLConfig) {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(mySQLConfig.driver());
            String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s", mySQLConfig.host(),
                    mySQLConfig.port(), mySQLConfig.database());
            LOG.info("Connecting to database {}", jdbcUrl);
            cpds.setJdbcUrl(jdbcUrl);

            cpds.setUser(mySQLConfig.user());
            cpds.setPassword(mySQLConfig.password());

            cpds.setMinPoolSize(mySQLConfig.minPoolSize());
            cpds.setMaxPoolSize(mySQLConfig.maxPoolSize());
        } catch (PropertyVetoException e) {
            throw new IllegalArgumentException(e);
        }

        return cpds;
    }
}
