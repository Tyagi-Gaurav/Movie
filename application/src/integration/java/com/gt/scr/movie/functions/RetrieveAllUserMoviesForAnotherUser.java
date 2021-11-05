package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

public class RetrieveAllUserMoviesForAnotherUser implements
        Interaction<WebTestClient, LoginResponseDTO, UUID, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient, LoginResponseDTO loginResponseDTO, UUID uuid) {
        try {
            return webTestClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/user/{userId}/movie").build(uuid.toString()))
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", loginResponseDTO.token()))
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.read.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
