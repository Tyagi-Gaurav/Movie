package com.gt.scr.movie.ext.user;

import com.google.common.net.HttpHeaders;
import com.gt.scr.ext.UpstreamClient;
import com.gt.scr.movie.exception.UnexpectedSystemException;
import com.gt.scr.resilience.Resilience;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class FetchUsersByIdClient implements UpstreamClient<UUID, UserDetailsResponseDTO> {
    public final WebClient webClient;

    public FetchUsersByIdClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Resilience("user")
    @Override
    public Mono<UserDetailsResponseDTO> execute(UUID uuid) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/user").queryParam("userId", uuid).build())
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.user.fetchByUserId.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.user.fetchByUserId.v1+json")
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(UserDetailsResponseDTO.class);
        } catch (Exception e) {
            return Mono.error(new UnexpectedSystemException(e));
        }
    }
}
