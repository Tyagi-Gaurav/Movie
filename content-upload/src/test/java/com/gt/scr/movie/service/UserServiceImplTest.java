package com.gt.scr.movie.service;

import com.gt.scr.movie.ext.user.FetchUsersByIdClient;
import com.gt.scr.movie.ext.user.FetchUsersByNameClient;
import com.gt.scr.movie.ext.user.UserDetailsResponseDTO;
import com.gt.scr.spc.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.gt.scr.movie.util.UserBuilder.aUser;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService userService;

    @Mock
    private FetchUsersByNameClient fetchUsersByNameClient;

    @Mock
    private FetchUsersByIdClient fetchUsersByIdClient;

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
        when(fetchUsersByNameClient.fetchUserBy(userName)).thenReturn(Mono.just(
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
                .expectNext(user)
                .expectComplete();
        verify(fetchUsersByNameClient).fetchUserBy(userName);
    }

    @Test
    void shouldReturnErrorWhenNoUserFoundByName() {
        String userName = "userName";
        when(fetchUsersByNameClient.fetchUserBy(userName)).thenReturn(Mono.empty());

        //when
        Mono<UserDetails> byUsername = userService.findByUsername(userName);

        //then
        StepVerifier.create(byUsername)
                .expectError(UsernameNotFoundException.class)
                .verify();
        verify(fetchUsersByNameClient).fetchUserBy(userName);
    }

    @Test
    void shouldFindUserByUserId() {
        UUID userId = UUID.randomUUID();
        User user = aUser().build();
        when(fetchUsersByIdClient.fetchUserBy(userId)).thenReturn(Mono.just(
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
        verify(fetchUsersByIdClient).fetchUserBy(userId);
    }
}