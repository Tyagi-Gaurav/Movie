package com.gt.scr.movie.ext.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.gt.scr.exception.DuplicateRecordException;
import com.gt.scr.movie.exception.UnexpectedSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CreateUserClient {
    public final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final Logger LOG = LoggerFactory.getLogger(CreateUserClient.class);

    public CreateUserClient(WebClient webClient,
                            ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public Mono<Void> createUser(UserCreateRequestDTO accountCreateRequestDTO) {
        try {
            LOG.info("Making call to create account to User Service");
            String objectBody = objectMapper.writeValueAsString(accountCreateRequestDTO);
            return webClient.post().uri("/api/user/account/create")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd+account.create.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd+account.create.v1+json")
                    .bodyValue(objectBody)
                    .retrieve()
                    .onStatus(HttpStatus::is2xxSuccessful, clientResponse -> Mono.empty())
                    .onStatus(httpStatus -> httpStatus.equals(HttpStatus.FORBIDDEN),
                            clientResponse -> Mono.error(new DuplicateRecordException("Duplicate user")))
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(Void.class);
        } catch (Exception e) {
            LOG.error("Exception occurred while invoking service");
            return Mono.error(new UnexpectedSystemException(e));
        }
    }
}
