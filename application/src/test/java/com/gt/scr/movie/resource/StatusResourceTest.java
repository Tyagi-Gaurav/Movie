package com.gt.scr.movie.resource;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class StatusResourceTest {
    private final StatusResource statusResource = new StatusResource();

    @Test
    void shouldReturnHelloWorldWithTime() {
        StepVerifier.create(statusResource.status())
                .expectNext("OK")
                .verifyComplete();
    }
}
