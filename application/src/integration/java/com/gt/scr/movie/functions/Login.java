package com.gt.scr.movie.functions;

import com.gt.scr.movie.resource.domain.LoginRequestDTO;
import com.gt.scr.movie.util.TestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.BiFunction;

public class Login
        implements BiFunction<WebTestClient, LoginRequestDTO, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient, LoginRequestDTO loginRequestDTO) {
        try {
            return webTestClient.post()
                    .uri("/user/login")
                    .bodyValue(TestUtils.asJsonString(loginRequestDTO))
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.login.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
