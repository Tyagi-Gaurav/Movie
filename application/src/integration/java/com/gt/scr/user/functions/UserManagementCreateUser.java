package com.gt.scr.user.functions;

import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.resource.domain.LoginResponseDTO;
import com.gt.scr.movie.util.TestUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

public class UserManagementCreateUser
        implements Interaction<WebTestClient, LoginResponseDTO, AccountCreateRequestDTO, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webTestClient,
                                            LoginResponseDTO loginResponseDTO,
                                            AccountCreateRequestDTO accountCreateRequestDTO) {
        try {
            return webTestClient.post().uri("/user/manage")
                    .bodyValue(TestUtils.asJsonString(accountCreateRequestDTO))
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.user.add.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.user.add.v1+json")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", loginResponseDTO.token()))
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
