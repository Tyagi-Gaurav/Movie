package com.gt.scr.resilience;

import java.time.Duration;

import static io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig.*;

public record ThreadPoolBulkheadConfig(boolean isEnabled,
                                       int coreThreadPoolSize,
                                       int maxThreadPoolSize,
                                       int keepAliveDurationInMs,
                                       int maxWaitDurationOfMs,
                                       int queueCapacity) {
    public io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig build() {
        return custom()
                .coreThreadPoolSize(getCoreThreadPoolSize())
                .maxThreadPoolSize(getMaxThreadPoolSize())
                .keepAliveDuration(getKeepAliveDuration())
                .queueCapacity(getQueueCapacity())
                .build();
    }

    private int getQueueCapacity() {
        return queueCapacity == 0 ? DEFAULT_QUEUE_CAPACITY : queueCapacity;
    }

    private int getCoreThreadPoolSize() {
        return coreThreadPoolSize == 0 ? DEFAULT_CORE_THREAD_POOL_SIZE : coreThreadPoolSize;
    }

    private int getMaxThreadPoolSize() {
        return maxThreadPoolSize == 0 ? DEFAULT_MAX_THREAD_POOL_SIZE : maxThreadPoolSize;
    }

    private Duration getKeepAliveDuration() {
        return keepAliveDurationInMs == 0 ? DEFAULT_KEEP_ALIVE_DURATION : Duration.ofMillis(keepAliveDurationInMs);
    }
}
