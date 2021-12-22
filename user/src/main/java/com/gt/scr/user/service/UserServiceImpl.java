package com.gt.scr.user.service;

import com.gt.scr.spc.domain.User;
import com.gt.scr.exception.DuplicateRecordException;
import com.gt.scr.user.dao.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(@Qualifier(value = "mysql") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> add(User user) {
        return userRepository.findUserBy(user.getUsername())
                .flatMap(user1 -> Mono.error(() -> new DuplicateRecordException("User already exists.")))
                .switchIfEmpty(Mono.defer(() -> {
                    LOG.info("Inserting user with name {} in database: ", user.getUsername());
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
    public Mono<User> findUserBy(UUID userId) {
        return userRepository.findUserBy(userId);
    }

    @Override
    public Mono<Void> update(User user) {
        return Mono.fromRunnable(() -> userRepository.update(user));
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findUserBy(username)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(() ->
                                new UsernameNotFoundException("Unable to find User in user application: " + username))))
                .map(Function.identity());
    }
}
