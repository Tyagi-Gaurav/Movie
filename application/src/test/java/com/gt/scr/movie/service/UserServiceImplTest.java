package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.UserRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.gt.scr.movie.util.UserBuilder.aUser;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserService userService;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(repository);
    }

    @Test
    void shouldAddUser() {
        User user = aUser().build();
        when(repository.findUserBy(user.getUsername())).thenReturn(Mono.empty());

        //when
        Mono<Void> add = userService.add(user);

        //then
        StepVerifier.create(add).verifyComplete();
        verify(repository).create(user);
    }

    @Test
    void shouldDeleteUser() {
        User user = aUser().build();
        when(repository.delete(user.id())).thenReturn(Mono.empty());

        //when
        Mono<Void> deleteMono = userService.deleteUser(user.id());

        //then
        StepVerifier.create(deleteMono).verifyComplete();
        verify(repository).delete(user.id());
    }

    @Test
    void shouldLoadUserByUserName() {
        String user = "userName";

        //when
        userService.loadUserBy(user);

        //then
        verify(repository).findUserBy(user);
    }

    @Test
    void shouldFindUserByUserName() {
        UUID userId = UUID.randomUUID();

        //when
        userService.findUserBy(userId);

        //then
        verify(repository).findUserBy(userId);
    }

    @Test
    void shouldUpdateUser() {
        User user = aUser().build();

        //when
        Mono<Void> update = userService.update(user);

        //then
        StepVerifier.create(update).verifyComplete();
        verify(repository).update(user);
    }

    @Test
    void shouldThrowExceptionWhenTryingToAddDuplicateUser() {
        //given
        User userA = aUser().build();
        User userB = aUser().withUserName(userA.getUsername()).build();
        when(repository.findUserBy(userA.getUsername())).thenReturn(Mono.empty()).thenReturn(Mono.just(userA));
        userService.add(userA);

        //when
        Mono<Void> add = userService.add(userB);

        //then
        StepVerifier.create(add)
                .expectError(DuplicateRecordException.class);
        verify(repository, times(2)).findUserBy(userA.getUsername());
    }
}