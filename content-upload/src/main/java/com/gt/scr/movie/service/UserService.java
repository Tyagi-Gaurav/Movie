package com.gt.scr.movie.service;


import com.gt.scr.spc.domain.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService extends ReactiveUserDetailsService {
    Mono<User> findUserBy(UUID userId);
}
