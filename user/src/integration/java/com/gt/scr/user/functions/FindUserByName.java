package com.gt.scr.user.functions;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Function;

public record FindUserByName(String userName) implements Function<WebTestClient, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient) {
        try {
            return webTestClient.get()
                    .uri(uriBuilder -> uriBuilder.queryParam("userName", userName)
                            .path("/user").build())
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.user.fetchByUserName.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.user.fetchByUserName.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
