package com.gt.scr.download.resource

import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

internal class DownloadResourceTest {
    @Test
    internal fun shouldReturnHelloWorld() {
        val movie = DownloadResource().getMovie()

        StepVerifier.create(movie)
            .expectNext("Hello World.")
            .verifyComplete()
    }
}