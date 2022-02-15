package com.gt.scr.user.functions;

import com.gt.scr.user.resource.domain.LoginRequestDTO;
import com.gt.scr.user.util.TestUtils;
import io.vertx.core.http.HttpHeaders;
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
                    .header(HttpHeaders.CONTENT_TYPE.toString(), "application/vnd.login.v1+json")
                    .header(HttpHeaders.ACCEPT.toString(), "application/vnd.login.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}

