package com.gt.scr.movie.system;

import com.gt.scr.movie.ext.user.StatusResponseDTO;
import com.gt.scr.movie.ext.user.UserStatusClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("userApp")
public class UserAppHealthCheck implements HealthIndicator {

    private final UserStatusClient userStatusClient;

    public UserAppHealthCheck(UserStatusClient userStatusClient) {
        this.userStatusClient = userStatusClient;
    }

    @Override
    public Health health() {
        StatusResponseDTO result = userStatusClient.status()
                .onErrorResume(throwable -> Mono.just(new StatusResponseDTO(throwable.getMessage())))
                .block();
        return result != null && "UP".equals(result.status()) ? Health.up().build() :
                Health.down(new RuntimeException("health check failed")).build();
    }
}
