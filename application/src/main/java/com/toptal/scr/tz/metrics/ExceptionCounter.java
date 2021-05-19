package com.toptal.scr.tz.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExceptionCounter {
    private final Counter exceptionCount;

    @Autowired
    public ExceptionCounter(MeterRegistry meterRegistry) {
        exceptionCount = Counter.builder("infraExceptionCount")
                .register(meterRegistry);
    }

    public void increment() {
        exceptionCount.increment();
    }
}
