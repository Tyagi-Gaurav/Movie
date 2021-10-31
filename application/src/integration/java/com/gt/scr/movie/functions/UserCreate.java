package com.gt.scr.movie.functions;

import com.google.common.net.HttpHeaders;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import com.gt.scr.movie.util.TestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.BiFunction;

public class UserCreate
        implements BiFunction<WebTestClient, AccountCreateRequestDTO, WebTestClient.ResponseSpec> {

    @Override
    public WebTestClient.ResponseSpec apply(WebTestClient webClient,
                                            AccountCreateRequestDTO accountCreateRequestDTO) {

        try {
            return webClient.post().uri("/user/account/create")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd+account.create.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd+account.create.v1+json")
                    .bodyValue(TestUtils.asJsonString(accountCreateRequestDTO))
                    .exchange();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
