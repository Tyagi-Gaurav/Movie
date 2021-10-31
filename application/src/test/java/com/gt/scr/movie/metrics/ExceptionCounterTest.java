package com.gt.scr.movie.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionCounterTest {
    private final MeterRegistry meterRegistry = new SimpleMeterRegistry();

    private final ExceptionCounter exceptionCounter = new ExceptionCounter(meterRegistry);

    @Test
    void shouldIncrementExceptionMetricWhenItOccurs() {
        //when
        exceptionCounter.increment(200);

        //then
        assertThat(meterRegistry.counter("exceptionCount", "status", "200")
                .count()).isEqualTo(1.0);
    }
}