package com.gt.scr.user.functions;

import io.vertx.core.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;
import java.util.function.Function;

public record FindUserById(UUID userId) implements Function<WebTestClient, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient) {
        try {
            return webTestClient.get()
                    .uri(uriBuilder -> uriBuilder.queryParam("userId", userId)
                            .path("/user").build())
                    .header(HttpHeaders.CONTENT_TYPE.toString(), "application/vnd.user.fetchByUserId.v1+json")
                    .header(HttpHeaders.ACCEPT.toString(), "application/vnd.user.fetchByUserId.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
