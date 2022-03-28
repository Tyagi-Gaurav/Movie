package com.gt.scr.movie.functions;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.BiFunction;

public class MovieRead
        implements BiFunction<WebTestClient,
        String,
        WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient,
                                            String token) {
        try {
            return webTestClient.get().uri("/user/movie")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.read.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.movie.read.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
