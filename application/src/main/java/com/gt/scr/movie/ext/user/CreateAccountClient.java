package com.gt.scr.movie.ext.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.gt.scr.movie.exception.UnexpectedSystemException;
import com.gt.scr.movie.resource.domain.AccountCreateRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CreateAccountClient {
    public final WebClient webClient;
    private final ObjectMapper objectMapper;

    public CreateAccountClient(WebClient webClient,
                               ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public Mono<Void> createAccount(AccountCreateRequestDTO accountCreateRequestDTO)  {
        try {
            return webClient.post().uri("/user/account/create")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd+account.create.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd+account.create.v1+json")
                    .bodyValue(objectMapper.writeValueAsString(accountCreateRequestDTO))
                    .retrieve()
                    .onStatus(HttpStatus::is2xxSuccessful, clientResponse -> Mono.empty())
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(Void.class);
        } catch (Exception e) {
            return Mono.error(new UnexpectedSystemException(e));
        }
    }
}
