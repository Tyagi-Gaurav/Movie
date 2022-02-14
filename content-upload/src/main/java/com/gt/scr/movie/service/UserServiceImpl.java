package com.gt.scr.movie.service;

import com.gt.scr.movie.ext.user.FetchUsersByIdClient;
import com.gt.scr.movie.ext.user.FetchUsersByNameClient;
import com.gt.scr.movie.ext.user.UserDetailsResponseDTO;
import com.gt.scr.spc.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {
    private final FetchUsersByNameClient fetchUsersByNameClient;
    private final FetchUsersByIdClient fetchUsersByIdClient;

    public UserServiceImpl(FetchUsersByNameClient fetchUsersByNameClient,
                           FetchUsersByIdClient fetchUsersByIdClient) {
        this.fetchUsersByNameClient = fetchUsersByNameClient;
        this.fetchUsersByIdClient = fetchUsersByIdClient;
    }

    @Override
    public Mono<User> findUserBy(UUID userId) {
        return fetchUsersByIdClient.fetchUserBy(userId)
                .map(UserDetailsResponseDTO::toUser)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(() ->
                                new UsernameNotFoundException("Unable to find User: " + userId))))
                .map(Function.identity());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
        return fetchUsersByNameClient.fetchUserBy(username)
                .map(UserDetailsResponseDTO::toUser)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(() ->
                                new UsernameNotFoundException("Unable to find User: " + username))))
                .map(Function.identity());
    }
}
