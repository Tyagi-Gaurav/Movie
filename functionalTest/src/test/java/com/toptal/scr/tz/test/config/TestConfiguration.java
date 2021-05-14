package com.toptal.scr.tz.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.toptal.scr.tz.test")
public class TestConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(10000);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

    @Bean
    @ConfigurationProperties("timezone-app")
    public TimeZoneAppConfig timeZoneAppConfig() {
        return ModifiableTimeZoneAppConfig.create();
    }
}
