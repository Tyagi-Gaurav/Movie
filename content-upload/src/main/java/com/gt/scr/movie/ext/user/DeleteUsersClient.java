package com.gt.scr.movie.ext.user;

import com.google.common.net.HttpHeaders;
import com.gt.scr.movie.exception.UnexpectedSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class DeleteUsersClient {
    public final WebClient webClient;

    public DeleteUsersClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Void> deleteUser(String userId, String token) {
        try {
            return webClient.delete()
                    .uri(uriBuilder -> uriBuilder.path("/api/user/manage")
                            .queryParam("userId", userId).build())
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.user.delete.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.user.delete.v1+json")
                    .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token))
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(Void.class);
        } catch (Exception e) {
            return Mono.error(new UnexpectedSystemException(e));
        }
    }
}
