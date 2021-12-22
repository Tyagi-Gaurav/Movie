package com.gt.scr.movie.service;

import com.gt.scr.domain.User;
import com.gt.scr.movie.ext.user.CreateUserClient;
import com.gt.scr.movie.ext.user.DeleteUsersClient;
import com.gt.scr.movie.ext.user.FetchUsersByIdClient;
import com.gt.scr.movie.ext.user.FetchUsersByNameClient;
import com.gt.scr.movie.ext.user.ListUsersClient;
import com.gt.scr.movie.ext.user.UserCreateRequestDTO;
import com.gt.scr.movie.ext.user.UserDetailsResponseDTO;
import com.gt.scr.movie.ext.user.UserListResponseDTO;
import com.gt.scr.movie.resource.SecurityContextHolder;
import com.gt.scr.movie.resource.domain.UserProfile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.UUID;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {
    private final CreateUserClient createUserClient;
    private final ListUsersClient listUsersClient;
    private final FetchUsersByNameClient fetchUsersByNameClient;
    private final FetchUsersByIdClient fetchUsersByIdClient;
    private final DeleteUsersClient deleteUsersClient;
    private final SecurityContextHolder securityContextHolder;

    public UserServiceImpl(CreateUserClient createUserClient,
                           ListUsersClient listUsersClient,
                           FetchUsersByNameClient fetchUsersByNameClient,
                           FetchUsersByIdClient fetchUsersByIdClient,
                           DeleteUsersClient deleteUsersClient,
                           SecurityContextHolder securityContextHolder) {
        this.createUserClient = createUserClient;
        this.listUsersClient = listUsersClient;
        this.fetchUsersByNameClient = fetchUsersByNameClient;
        this.fetchUsersByIdClient = fetchUsersByIdClient;
        this.deleteUsersClient = deleteUsersClient;
        this.securityContextHolder = securityContextHolder;
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
        return securityContextHolder.getContext(UserProfile.class)
                .flatMapMany(up -> listUsersClient.listAllUsers(up.token())
                        .flatMapIterable(UserListResponseDTO::userDetails)
                        .map(userDetailsResponse -> new User(
                                userDetailsResponse.id(),
                                userDetailsResponse.firstName(),
                                userDetailsResponse.lastName(),
                                userDetailsResponse.userName(),
                                "",
                                Collections.singletonList(new SimpleGrantedAuthority(userDetailsResponse.role()))
                        )));
    }

    @Override
    public Mono<Void> deleteUser(UUID userId) {
        return securityContextHolder.getContext(UserProfile.class)
                .flatMap(up -> deleteUsersClient.deleteUser(userId.toString(), up.token()));
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
    public Mono<Void> update(User user) {
//        return Mono.fromRunnable(() -> userRepository.update(user));
        return Mono.empty();
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
