package com.gt.scr.user.service;


import com.gt.scr.user.service.domain.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService extends ReactiveUserDetailsService {
    Mono<Void> add(User user);

    Flux<User> getAllUsers();

    Mono<Void> deleteUser(UUID userId);

    Mono<User> findUserBy(UUID userId);

    Mono<Void> update(User user);
}
