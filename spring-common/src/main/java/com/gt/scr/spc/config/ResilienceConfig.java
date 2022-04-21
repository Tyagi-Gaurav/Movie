package com.gt.scr.spc.config;

import com.gt.scr.resilience.CircuitBreakerConfig;
import com.gt.scr.resilience.ThreadPoolBulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Map;
import java.util.stream.Collectors;

@ConstructorBinding
@ConfigurationProperties("resilience")
public record ResilienceConfig(Map<String, CircuitBreakerConfig> circuitBreaker,
                               Map<String, ThreadPoolBulkheadConfig> threadPoolBulkhead) {

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
}
