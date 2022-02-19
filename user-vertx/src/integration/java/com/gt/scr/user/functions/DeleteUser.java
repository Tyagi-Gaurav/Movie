package com.gt.scr.user.functions;

import com.gt.scr.user.resource.domain.LoginResponseDTO;
import io.vertx.core.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.BiFunction;

public record DeleteUser(LoginResponseDTO loginResponseDTO)
        implements BiFunction<WebTestClient, LoginResponseDTO, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient, LoginResponseDTO adminLoginResponseDTO) {
        try {
            return webTestClient.delete()
                    .uri(uriBuilder -> uriBuilder.queryParam("userId", loginResponseDTO.id())
                            .path("/user/manage").build())
                    .header(HttpHeaders.AUTHORIZATION.toString(), String.format("Bearer %s", adminLoginResponseDTO.token()))
                    .header(HttpHeaders.CONTENT_TYPE.toString(), "application/vnd.user.delete.v1+json")
                    .header(HttpHeaders.ACCEPT.toString(), "application/vnd.user.delete.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
