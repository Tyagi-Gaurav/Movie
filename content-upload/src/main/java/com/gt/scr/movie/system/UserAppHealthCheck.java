package com.gt.scr.movie.system;

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
        String result = userStatusClient.status()
                .onErrorResume(throwable -> Mono.just(throwable.getMessage()))
                .block();
        return "UP".equals(result) ? Health.up().build() : Health.down(new RuntimeException(result)).build();
    }
}
