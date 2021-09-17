package com.gt.scr.movie.test.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.gt.scr.movie.test")
public class TestConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    @Bean
    @ConfigurationProperties("movie-app")
    public MovieAppConfig movieAppConfig() {
        return ModifiableMovieAppConfig.create();
    }

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forTarget("localhost:8900")
                .usePlaintext().build();
    }
}
