package com.gt.scr.movie.ext.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.gt.scr.movie.exception.UnexpectedSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CreateUserByAdminClient {
    public final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(CreateUserByAdminClient.class);

    public CreateUserByAdminClient(WebClient webClient,
                                   ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public Mono<Void> createUser(String token, UserCreateRequestDTO accountCreateRequestDTO) {
        try {
            LOG.info("Making call to create account to User Service");
            String objectBody = objectMapper.writeValueAsString(accountCreateRequestDTO);
            return webClient.post().uri("/api/user/manage")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.user.add.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.user.add.v1+json")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                    .bodyValue(objectBody)
                    .retrieve()
                    .onStatus(HttpStatus::is2xxSuccessful, clientResponse -> Mono.empty())
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(Void.class);
        } catch (Exception e) {
            LOG.error("Exception occurred while invoking service");
            return Mono.error(new UnexpectedSystemException(e));
        }
    }
}
