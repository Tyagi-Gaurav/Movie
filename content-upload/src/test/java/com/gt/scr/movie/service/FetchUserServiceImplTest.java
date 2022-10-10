package com.gt.scr.movie.service;

import com.gt.scr.domain.User;
import com.gt.scr.movie.ext.user.FetchUsersByNameClient;
import com.gt.scr.ext.UpstreamClient;
import com.gt.scr.movie.ext.user.UserDetailsResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.UUID;

import static com.gt.scr.movie.util.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchUserServiceImplTest {
    private FetchUserService userService;

    @Mock
    private FetchUsersByNameClient fetchUsersByNameClient;

    @Mock
    private UpstreamClient<UUID, UserDetailsResponseDTO> fetchUsersByIdClient;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                fetchUsersByNameClient,
                fetchUsersByIdClient);
    }

    @Test
    void shouldFindUserByUserName() {
        String userName = "userName";
        User user = aUser().withUserName(userName).build();
        when(fetchUsersByNameClient.execute(userName)).thenReturn(Mono.just(
                new UserDetailsResponseDTO(
                        user.username(),
                        user.password(),
                        user.firstName(), user.lastName(), user.getRole(),
                        user.id()
                )
        ));

        //when
        Mono<UserDetails> byUsername = userService.findByUsername(userName);

        //then
        StepVerifier.create(byUsername)
                .consumeNextWith(userDetails -> {
                    assertThat(userDetails.getUsername()).isEqualTo(user.username());
                    assertThat(userDetails.getPassword()).isEqualTo(user.password());
                    assertThat(userDetails.getAuthorities()).isNotEmpty();
                    assertThat(userDetails.getAuthorities()).isEqualTo(
                            Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
                })
                .expectComplete();
        verify(fetchUsersByNameClient).execute(userName);
    }

    @Test
    void shouldReturnErrorWhenNoUserFoundByName() {
        String userName = "userName";
        when(fetchUsersByNameClient.execute(userName)).thenReturn(Mono.empty());

        //when
        Mono<UserDetails> byUsername = userService.findByUsername(userName);

        //then
        StepVerifier.create(byUsername)
                .expectError(UsernameNotFoundException.class)
                .verify();
        verify(fetchUsersByNameClient).execute(userName);
    }

    @Test
    void shouldFindUserByUserId() {
        UUID userId = UUID.randomUUID();
        User user = aUser().build();
        when(fetchUsersByIdClient.execute(userId)).thenReturn(Mono.just(
                new UserDetailsResponseDTO(
                        user.username(),
                        user.password(),
                        user.firstName(), user.lastName(), user.getRole(),
                        userId
                )
        ));

        //when
        userService.findUserBy(userId);

        //then
        verify(fetchUsersByIdClient).execute(userId);
    }
}