package com.gt.scr.user.resource;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class StatusResourceTest {
    private final StatusResource statusResource = new StatusResource();

    @Test
    void shouldReturnHelloWorldWithTime() {
        StepVerifier.create(statusResource.status())
                .expectNext("UP")
                .verifyComplete();
    }
}
