package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.UserRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(@Qualifier(value = "mysql") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void add(User user) {
        userRepository.findUserBy(user.getUsername())
                .flatMap(user1 -> Mono.error(() -> new DuplicateRecordException("User already exists.")))
                .switchIfEmpty(Mono.defer(() -> {
                    userRepository.create(user);
                    return Mono.empty();
                })).block();
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.delete(userId);
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
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Mono<User> userMono = loadUserBy(username)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(() ->
                                new UsernameNotFoundException("Unable to find User: " + username))));

        return userMono.block();
    }
}
