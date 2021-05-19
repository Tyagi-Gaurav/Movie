package com.toptal.scr.tz.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExceptionCounter {
    private static final String STATUS_TAG = "status";

    @Autowired
    private MeterRegistry meterRegistry;

    public void increment(int status) {
        Counter exceptionCount = Counter.builder("exceptionCount")
                .tags(STATUS_TAG, String.valueOf(status))
                .register(meterRegistry);
        exceptionCount.increment();
    }
}
