package com.gt.scr.user.functions;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;
import java.util.function.Function;

public record DeleteUser(UUID userId) implements Function<WebTestClient, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient) {
        try {
            return webTestClient.delete()
                    .uri(uriBuilder -> uriBuilder.queryParam("userId", userId)
                            .path("/user").build())
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.user.deleteUser.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.user.deleteUser.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
