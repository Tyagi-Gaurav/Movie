package com.gt.scr.movie.service;


import com.gt.scr.movie.service.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    void add(User user);

    Flux<User> getAllUsers();

    void deleteUser(UUID userId);

    Mono<User> loadUserBy(String username);

    Mono<User> findUserBy(UUID userId);

    void update(User user);
}
