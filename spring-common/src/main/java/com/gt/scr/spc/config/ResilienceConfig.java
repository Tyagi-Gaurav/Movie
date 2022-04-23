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
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@ConstructorBinding
@ConfigurationProperties("resilience")
public record ResilienceConfig(Map<String, CircuitBreakerConfig> circuitBreaker,
                               Map<String, ThreadPoolBulkheadConfig> threadPoolBulkhead,
                               Map<String, TimeLimitConfig> timeLimitConfig) {

    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return Optional.ofNullable(circuitBreaker)
                .map(cb ->
                        CircuitBreakerRegistry.of(cb.entrySet()
                                .stream()
                                .filter(entry -> entry.getValue().isEnabled())
                                .collect(Collectors.toMap(Map.Entry::getKey,
                                        entry -> entry.getValue().build())))
                ).orElseGet(CircuitBreakerRegistry::ofDefaults);
    }

    public ThreadPoolBulkheadRegistry threadPoolBulkHeadRegistry() {
        return Optional.ofNullable(threadPoolBulkhead)
                .map(tbh -> ThreadPoolBulkheadRegistry.of(tbh.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().isEnabled())
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                entry -> entry.getValue().build()))))
                .orElseGet(ThreadPoolBulkheadRegistry::ofDefaults);
    }

    public TimeLimiterRegistry timeLimiterRegistry() {
        return Optional.ofNullable(timeLimitConfig)
                .map(tbh -> TimeLimiterRegistry.of(tbh.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue().isEnabled())
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                entry -> entry.getValue().build()))))
                .orElseGet(TimeLimiterRegistry::ofDefaults);
    }

    @Bean
    @Qualifier("resilienceExecutorService")
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(10);
    }
}
