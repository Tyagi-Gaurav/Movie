package com.gt.scr.spc.config;

import com.gt.scr.resilience.CircuitBreakerConfig;
import com.gt.scr.resilience.ThreadPoolBulkheadConfig;
import com.gt.scr.resilience.TimeLimitConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@ConstructorBinding
@ConfigurationProperties("resilience")
public record ResilienceConfig(Map<String, CircuitBreakerConfig> circuitBreaker,
                               Map<String, ThreadPoolBulkheadConfig> threadPoolBulkhead,
                               Map<String, TimeLimitConfig> timeLimit) {

    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.of(circuitBreaker.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isEnabled())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().build())));
    }

    public ThreadPoolBulkheadRegistry threadPoolBulkHeadRegistry() {
        return ThreadPoolBulkheadRegistry.of(threadPoolBulkhead.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isEnabled())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().build())));
    }

    public TimeLimiterRegistry timeLimiterRegistry() {
        return TimeLimiterRegistry.of(timeLimit.entrySet()
                .stream()
                .filter(entry -> entry.getValue().isEnabled())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().build())));
    }

    @Bean
    @Qualifier("ResilienceTimeLimiterScheduler")
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(10);
    }
}
