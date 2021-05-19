package com.toptal.scr.tz.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class LastTimeHelloWorldAccessedGauge {
    private final Gauge lastAccessedTime;

    public LastTimeHelloWorldAccessedGauge(MeterRegistry meterRegistry) {
        lastAccessedTime = Gauge.builder("last_accessed_time",
                () -> Instant.now().getEpochSecond())
                .register(meterRegistry);
    }

    public void setValue() {
        lastAccessedTime.value();
    }
}
