package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.UserRepository;
import com.gt.scr.movie.ext.user.CreateUserClient;
import com.gt.scr.movie.ext.user.UserCreateRequestDTO;
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
    private final CreateUserClient createUserClient;
    private final UserRepository userRepository;

    public UserServiceImpl(CreateUserClient createUserClient,
                           @Qualifier(value = "mysql") UserRepository userRepository) {
        this.createUserClient = createUserClient;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> add(User user) {
        return createUserClient.createUser(new UserCreateRequestDTO(
                user.username(),
                user.password(),
                user.firstName(),
                user.lastName(),
                user.getRole()
        ));
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
                                new UsernameNotFoundException("Unable to find User: " + username))))
                .map(Function.identity());
    }
}
