package com.gt.scr.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import java.time.Duration;

public class EndpointHistogram {
    private final Timer histogram;

    public EndpointHistogram(MeterRegistry meterRegistry) {
        //1ms to 10 seconds
        histogram = Timer.builder("request_latency")
                .publishPercentiles(0.5, 0.95, 0.99)
                .register(meterRegistry);
    }

    public void observe(long duration) {
        histogram.record(Duration.ofMillis(duration));
    }
}
