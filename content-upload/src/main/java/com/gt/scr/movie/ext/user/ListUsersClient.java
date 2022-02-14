package com.gt.scr.movie.ext.user;

import com.google.common.net.HttpHeaders;
import com.gt.scr.movie.exception.UnexpectedSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ListUsersClient {
    public final WebClient webClient;

    public ListUsersClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<UserListResponseDTO> listAllUsers(String token) {
        try {
            return webClient.get().uri("/api/user/manage")
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.user.read.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.user.read.v1+json")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(UserListResponseDTO.class);
        } catch (Exception e) {
            return Mono.error(new UnexpectedSystemException(e));
        }
    }
}
