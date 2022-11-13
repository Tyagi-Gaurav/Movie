package com.gt.scr.saga_webflux;

import org.reactivestreams.Publisher;

public class SagaMain {
    public static void main(String[] args) {
        Publisher<Integer> integerPublisher = SagaRunner.<Integer>instance()
                .sync()
                .parallel()
                .aggregate()
                .toResult();

        integerPublisher.subscribe();
    }
}
