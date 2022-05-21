package com.gt.scr.movie.functions;

import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;
import java.util.function.BiFunction;

public class MovieStreamAccess implements BiFunction<UUID, WebTestClient, WebTestClient.ResponseSpec> {
    @Override
    public WebTestClient.ResponseSpec apply(UUID uuid, WebTestClient webTestClient) {
        return null;
    }
}
