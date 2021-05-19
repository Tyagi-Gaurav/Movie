package com.toptal.scr.tz.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EndpointRequestCounter {
    private final Counter requestCount;
    private static final String METHOD_TAG = "method";
    private static final String PATH_TAG = "path";
    private static final String INFRA_REQUEST_COUNT = "infra_request_count";;

    @Autowired
    public EndpointRequestCounter(MeterRegistry meterRegistry) {
        requestCount = Counter.builder(INFRA_REQUEST_COUNT)
                .tags(METHOD_TAG, "", PATH_TAG, "")
                .register(meterRegistry);
    }

    public void increment(String method, String path) {
        Metrics.counter(INFRA_REQUEST_COUNT, METHOD_TAG, method, PATH_TAG, path).increment();
        requestCount.increment();
    }
}
