package com.gt.scr.resilience;

import java.time.Duration;

import static io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.*;

public record CircuitBreakerConfig(boolean isEnabled,
                                   int failureRateThreshold,
                                   int slowCallRateThreshold,
                                   int slowCallDurationThresholdInMs,
                                   int minimumNumberOfCalls) {
    public io.github.resilience4j.circuitbreaker.CircuitBreakerConfig build() {
        return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                .failureRateThreshold(getFailureRateThreshold())
                .slowCallRateThreshold(getSlowCallRateThreshold())
                .slowCallDurationThreshold(getSlowCallDurationThreshold())
                .minimumNumberOfCalls(getMinimumNumberOfCalls())
                .build();
    }

    private int getMinimumNumberOfCalls() {
        return minimumNumberOfCalls == 0 ? DEFAULT_MINIMUM_NUMBER_OF_CALLS : minimumNumberOfCalls;
    }

    private Duration getSlowCallDurationThreshold() {
        return slowCallDurationThresholdInMs == 0 ?
                Duration.ofSeconds(DEFAULT_SLOW_CALL_DURATION_THRESHOLD) :
                Duration.ofMillis(slowCallDurationThresholdInMs);
    }

    private float getSlowCallRateThreshold() {
        return slowCallRateThreshold == 0 ? DEFAULT_SLOW_CALL_RATE_THRESHOLD : slowCallRateThreshold;
    }

    private float getFailureRateThreshold() {
        return failureRateThreshold == 0 ? DEFAULT_FAILURE_RATE_THRESHOLD : failureRateThreshold;
    }


}
