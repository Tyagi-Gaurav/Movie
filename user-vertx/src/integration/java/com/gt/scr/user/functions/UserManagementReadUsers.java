package com.gt.scr.user.functions;

import com.gt.scr.user.resource.domain.LoginResponseDTO;
import io.vertx.core.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.BiFunction;

public class UserManagementReadUsers implements BiFunction<WebTestClient, LoginResponseDTO, WebTestClient.ResponseSpec> {
    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient, LoginResponseDTO loginResponseDTO) {
        try {
            return webTestClient.get().uri("/user/manage")
                    .header(HttpHeaders.AUTHORIZATION.toString(), String.format("Bearer %s", loginResponseDTO.token()))
                    .header(HttpHeaders.CONTENT_TYPE.toString(), "application/vnd.user.read.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
