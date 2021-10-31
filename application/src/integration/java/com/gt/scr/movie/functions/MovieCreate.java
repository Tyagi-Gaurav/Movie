package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import com.gt.scr.movie.resource.domain.MovieCreateRequestDTO;
import com.gt.scr.movie.util.TestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

public class MovieCreate
        implements Interaction<WebTestClient,
                LoginResponseDTO,
                MovieCreateRequestDTO, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient,
                           LoginResponseDTO loginResponseDTO,
                           MovieCreateRequestDTO movieCreateRequestDTO) {
        try {
            return webTestClient.post().uri("/user/movie")
                    .bodyValue(TestUtils.asJsonString(movieCreateRequestDTO))
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", loginResponseDTO.token()))
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.movie.add.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
