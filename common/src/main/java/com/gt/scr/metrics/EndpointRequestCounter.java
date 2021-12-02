package com.gt.scr.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class EndpointRequestCounter {
    private static final String METHOD_TAG = "method";
    private static final String PATH_TAG = "path";
    private static final String INFRA_REQUEST_COUNT = "request_count";

    private final MeterRegistry meterRegistry;

    public EndpointRequestCounter(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void increment(String method, String path) {
        Counter requestCount = Counter.builder(INFRA_REQUEST_COUNT)
                .tags(METHOD_TAG, method, PATH_TAG, path)
                .register(meterRegistry);
        requestCount.increment();
    }
}
