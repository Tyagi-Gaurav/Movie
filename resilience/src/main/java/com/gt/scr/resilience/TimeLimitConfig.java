package com.gt.scr.resilience;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import java.time.Duration;

public record TimeLimitConfig(boolean isEnabled, int timeoutDurationInMs) {
    public io.github.resilience4j.timelimiter.TimeLimiterConfig build() {
        return TimeLimiterConfig.custom()
                .timeoutDuration(getTimeoutDuration())
                .build();
    }

    private Duration getTimeoutDuration() {
        return timeoutDurationInMs == 0 ? Duration.ofSeconds(10) : Duration.ofMillis(timeoutDurationInMs);
    }


}
