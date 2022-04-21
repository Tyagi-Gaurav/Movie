package com.gt.scr.spc.config;

import com.gt.scr.resilience.CircuitBreakerConfig;
import com.gt.scr.resilience.ThreadPoolBulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.ConfigurationNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ResilienceConfigTest {
    @Test
    void shouldNotIncludeCircuitBreakerIfItsDisabled() {
        ResilienceConfig cb = new ResilienceConfig(Map.of("cb",
                new CircuitBreakerConfig(false, 20, 20, 20)), Collections.emptyMap());

        CircuitBreakerRegistry circuitBreakerRegistry = cb.circuitBreakerRegistry();

        assertThatExceptionOfType(ConfigurationNotFoundException.class)
                .isThrownBy(() -> circuitBreakerRegistry.circuitBreaker("cbName", "cb"));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void shouldNotIncludeCircuitBreakerIfItsNullOrEmpty(Map<String, CircuitBreakerConfig> cbMap) {
        ResilienceConfig cb = new ResilienceConfig(cbMap, Collections.emptyMap());

        CircuitBreakerRegistry circuitBreakerRegistry = cb.circuitBreakerRegistry();

        assertThat(circuitBreakerRegistry.getAllCircuitBreakers()).isEmpty();
    }

    @Test
    void shouldIncludeCircuitBreakerIfItsEnabled() {
        CircuitBreakerConfig circuitBreakerConfig = new CircuitBreakerConfig(true, 20,
                20, 20);
        ResilienceConfig resilienceConfig = new ResilienceConfig(Map.of("cbConfig", circuitBreakerConfig), Collections.emptyMap());
        io.github.resilience4j.circuitbreaker.CircuitBreakerConfig expectedConfig = circuitBreakerConfig.build();

        CircuitBreakerRegistry circuitBreakerRegistry = resilienceConfig.circuitBreakerRegistry();

        String circuitBreakerName = "cbName";
        CircuitBreaker expectedCircuitBreaker = circuitBreakerRegistry.circuitBreaker(circuitBreakerName, "cbConfig");
        assertThat(expectedCircuitBreaker.getName()).isEqualTo(circuitBreakerName);
        assertThat(expectedCircuitBreaker.getCircuitBreakerConfig().getFailureRateThreshold()).isEqualTo(expectedConfig.getFailureRateThreshold());
        assertThat(expectedCircuitBreaker.getCircuitBreakerConfig().getSlowCallDurationThreshold()).isEqualTo(expectedConfig.getSlowCallDurationThreshold());
        assertThat(expectedCircuitBreaker.getCircuitBreakerConfig().getSlowCallRateThreshold()).isEqualTo(expectedConfig.getSlowCallRateThreshold());
    }

    @Test
    void shouldNotIncludeBulkHeadIfItsDisabled() {
        ResilienceConfig cb = new ResilienceConfig(
                Collections.emptyMap(), Map.of("bh",
                new ThreadPoolBulkheadConfig(false, 20, 20, 20, 20, 10)));

        ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry = cb.threadPoolBulkHeadRegistry();

        assertThatExceptionOfType(ConfigurationNotFoundException.class)
                .isThrownBy(() -> threadPoolBulkheadRegistry.bulkhead("bhName", "bh"));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void shouldNotIncludeBulkheadIfItsNullOrEmpty(Map<String, ThreadPoolBulkheadConfig> bulkheadConfigMap) {
        ResilienceConfig cb = new ResilienceConfig(Collections.emptyMap(), bulkheadConfigMap);

        ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry = cb.threadPoolBulkHeadRegistry();

        assertThat(threadPoolBulkheadRegistry.getAllBulkheads()).isEmpty();
    }

    @Test
    void shouldIncludeBulkHeadIfItsEnabled() {
        ThreadPoolBulkheadConfig bulkHeadConfig = new ThreadPoolBulkheadConfig(true, 20, 20, 20, 20, 10);
        ResilienceConfig resilienceConfig = new ResilienceConfig(
                Collections.emptyMap(), Map.of("bh", bulkHeadConfig));
        io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig expectedConfig = bulkHeadConfig.build();

        ThreadPoolBulkheadRegistry threadPoolBulkheadRegistry = resilienceConfig.threadPoolBulkHeadRegistry();

        String bulkHeadName = "bhName";
        ThreadPoolBulkhead threadPoolBulkhead = threadPoolBulkheadRegistry.bulkhead(bulkHeadName, "bh");
        assertThat(threadPoolBulkhead.getName()).isEqualTo(bulkHeadName);
        assertThat(threadPoolBulkhead.getBulkheadConfig().getCoreThreadPoolSize()).isEqualTo(expectedConfig.getCoreThreadPoolSize());
        assertThat(threadPoolBulkhead.getBulkheadConfig().getMaxThreadPoolSize()).isEqualTo(expectedConfig.getMaxThreadPoolSize());
        assertThat(threadPoolBulkhead.getBulkheadConfig().getKeepAliveDuration()).isEqualTo(expectedConfig.getKeepAliveDuration());
        assertThat(threadPoolBulkhead.getBulkheadConfig().getQueueCapacity()).isEqualTo(expectedConfig.getQueueCapacity());
    }
}