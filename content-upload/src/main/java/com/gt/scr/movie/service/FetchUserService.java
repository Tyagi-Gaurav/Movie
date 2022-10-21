package com.gt.scr.movie.service;


import com.gt.scr.domain.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FetchUserService extends ReactiveUserDetailsService {
    Mono<User> findUserBy(UUID userId);
}
