package com.gt.scr.user.dao;

import com.gt.scr.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository {
    Mono<User> findUserBy(UUID userId);

    Mono<User> findUserBy(String userName);

    Flux<User> getAllUsers();

    Mono<Void> delete(UUID userId);

    Mono<Void> update(User user);

    default void create(User user) {}
}
