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

@Component
public class FetchUsersByNameClient implements UpstreamClient<String, UserDetailsResponseDTO> {
    public final WebClient webClient;

    public FetchUsersByNameClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Resilience
    @Override
    public Mono<UserDetailsResponseDTO> execute(String userName) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/user").queryParam("userName", userName).build())
                    .header(HttpHeaders.CONTENT_TYPE, "application/vnd.user.fetchByUserName.v1+json")
                    .header(HttpHeaders.ACCEPT, "application/vnd.user.fetchByUserName.v1+json")
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(UserDetailsResponseDTO.class);
        } catch (Exception e) {
            return Mono.error(new UnexpectedSystemException(e));
        }
    }
}
