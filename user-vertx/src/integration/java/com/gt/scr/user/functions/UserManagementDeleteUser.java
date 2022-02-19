package com.gt.scr.user.functions;

import com.gt.scr.user.resource.domain.LoginResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

public class UserManagementDeleteUser
        implements Interaction<WebTestClient, LoginResponseDTO, String, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient,
                           LoginResponseDTO loginResponseDTO,
                           String userId) {
        try {
            return webTestClient.delete()
                    .uri(uriBuilder -> uriBuilder.path("/user/manage").queryParam("userId", userId).build())
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", loginResponseDTO.token()))
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.user.delete.v1+json")
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
