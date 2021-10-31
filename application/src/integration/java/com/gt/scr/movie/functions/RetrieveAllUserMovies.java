package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.BiFunction;

public class RetrieveAllUserMovies implements BiFunction<WebTestClient, LoginResponseDTO, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient, LoginResponseDTO loginResponseDTO) {
        try {
            return webTestClient.get().uri("/user/movie")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", loginResponseDTO.token()))
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.read.v1+json")
                    .exchange().expectStatus().isOk();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
