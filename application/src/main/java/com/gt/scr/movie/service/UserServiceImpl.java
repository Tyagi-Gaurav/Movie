package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.UserRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(@Qualifier(value = "mysql") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> add(User user) {
        return userRepository.findUserBy(user.getUsername())
                .flatMap(user1 -> Mono.error(() -> new DuplicateRecordException("User already exists.")))
                .switchIfEmpty(Mono.defer(() -> {
                    userRepository.create(user);
                    return Mono.empty();
                })).then();
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public Mono<Void> deleteUser(UUID userId) {
        return userRepository.delete(userId);
    }

    @Override
    public Mono<User> loadUserBy(String userName) {
        return userRepository.findUserBy(userName);
    }

    @Override
    public Mono<User> findUserBy(UUID userId) {
        return userRepository.findUserBy(userId);
    }

    @Override
    public Mono<Void> update(User user) {
        return Mono.fromRunnable(() -> userRepository.update(user));
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
        return loadUserBy(username)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(() ->
                                new UsernameNotFoundException("Unable to find User: " + username))))
                .map(Function.identity());
    }
}
