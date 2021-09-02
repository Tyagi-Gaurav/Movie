package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.User;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findUserBy(UUID userId);

    Optional<User> findUserBy(String userName);

    Flux<User> getAllUsers();

    void delete(UUID userId);

    void update(User user);

    default void create(User user) {}
}
