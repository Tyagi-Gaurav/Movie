package com.gt.scr.saga_webflux;

import org.reactivestreams.Publisher;

/**
 *
 * ParallelValidations() -> Synchronous Call 1 (Calls External Systems) -> Synchronous/Parallel call using results
 *
 * @param <T>
 */
public class SagaRunner<T> {

    private SagaRunner() {}

    public static <T> SagaRunner<T> instance() {
        return new SagaRunner<>();
    }

    public SagaRunner<T> sync(Publisher<T>... publishers) {
        return this;
    }

    public SagaRunner<T> parallel(Publisher<T>... publishers) {
        return this;
    }


    public SagaRunner<T> aggregate() {
        return this;
    }

    public Publisher<T> toResult() {
        return null;
    }
}
