package com.gt.scr.movie.ext.user;

import com.gt.scr.ext.UpstreamClient;
import com.gt.scr.movie.exception.UnexpectedSystemException;
import com.gt.scr.resilience.Resilience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserStatusClient implements UpstreamClient<Void, StatusResponseDTO>  {
    public final WebClient webClient;

    private static final Logger LOG = LoggerFactory.getLogger(UserStatusClient.class);

    public UserStatusClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    @Resilience("user")
    public Mono<StatusResponseDTO> execute(Void unused) {
        try {
            return webClient.get().uri("/api/status")
                    .retrieve()
                    .onStatus(HttpStatus::isError, ClientResponse::createException)
                    .bodyToMono(StatusResponseDTO.class)
                    .doOnSuccess(statusResponseDTO ->
                            LOG.info("Got response {}", statusResponseDTO)
                    )
                    .doOnError(throwable ->
                            LOG.error("Error Occurred: " + throwable.getMessage(), throwable));
        } catch (Exception e) {
            LOG.error("Exception occurred while invoking service");
            return Mono.error(new UnexpectedSystemException(e));
        }
    }
}
