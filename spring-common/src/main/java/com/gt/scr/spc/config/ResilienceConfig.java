package com.gt.scr.spc.config;

import com.gt.scr.resilience.CircuitBreakerConfig;
import com.gt.scr.resilience.ThreadPoolBulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ConstructorBinding
@ConfigurationProperties("resilience")
public record ResilienceConfig(Map<String, CircuitBreakerConfig> circuitBreaker,
                               Map<String, ThreadPoolBulkheadConfig> threadPoolBulkhead) {

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
}
