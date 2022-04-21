package com.gt.scr.movie.system;

import com.gt.scr.ext.UpstreamClient;
import com.gt.scr.movie.ext.user.StatusResponseDTO;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("userApp")
public class UserAppHealthCheck implements HealthIndicator {

    private final UpstreamClient<Void, StatusResponseDTO> userStatusClient;

    public UserAppHealthCheck(UpstreamClient<Void, StatusResponseDTO> userStatusClient) {
        this.userStatusClient = userStatusClient;
    }

    @Override
    public Health health() {
        StatusResponseDTO result = userStatusClient.execute(null)
                .onErrorResume(throwable -> Mono.just(new StatusResponseDTO(throwable.getMessage())))
                .block();
        return result != null && "UP".equals(result.status()) ? Health.up().build() :
                Health.down(new RuntimeException("health check failed")).build();
    }
}
