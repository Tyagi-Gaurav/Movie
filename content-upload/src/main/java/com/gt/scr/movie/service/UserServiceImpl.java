package com.gt.scr.movie.service;

import com.gt.scr.domain.User;
import com.gt.scr.ext.UpstreamClient;
import com.gt.scr.movie.ext.user.UserDetailsResponseDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {
    private final UpstreamClient<String, UserDetailsResponseDTO> fetchUsersByNameClient;
    private final UpstreamClient<UUID, UserDetailsResponseDTO> fetchUsersByIdClient;

    public UserServiceImpl(UpstreamClient<String, UserDetailsResponseDTO> fetchUsersByNameClient,
                           UpstreamClient<UUID, UserDetailsResponseDTO> fetchUsersByIdClient) {
        this.fetchUsersByNameClient = fetchUsersByNameClient;
        this.fetchUsersByIdClient = fetchUsersByIdClient;
    }

    @Override
    public Mono<User> findUserBy(UUID userId) {
        return fetchUsersByIdClient.execute(userId)
                .map(UserDetailsResponseDTO::toUser)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(() ->
                                new UsernameNotFoundException("Unable to find User: " + userId))))
                .map(Function.identity());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
        return fetchUsersByNameClient.execute(username)
                .map(this::toUserDetails)
                .switchIfEmpty(
                        Mono.defer(() -> Mono.error(() ->
                                new UsernameNotFoundException("Unable to find User: " + username))))
                .map(Function.identity());
    }

    private UserDetails toUserDetails(UserDetailsResponseDTO user) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new SimpleGrantedAuthority(user.role()));
            }

            @Override
            public String getPassword() {
                return user.password();
            }

            @Override
            public String getUsername() {
                return user.userName();
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }
}
