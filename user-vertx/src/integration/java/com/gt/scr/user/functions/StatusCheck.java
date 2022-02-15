package com.gt.scr.user.functions;

import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Function;

public record StatusCheck() implements Function<WebTestClient, WebTestClient.ResponseSpec> {
    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient) {
        try {
            return webTestClient.get()
                    .uri("/status")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
