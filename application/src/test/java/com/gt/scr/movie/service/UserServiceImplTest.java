package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.UserRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.gt.scr.movie.util.UserBuilder.aUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @MockBean
    @Qualifier("mysql")
    private UserRepository repository;

    @Test
    void shouldAddUser() {
        User user = aUser().build();
        when(repository.findUserBy(user.getUsername())).thenReturn(Mono.empty());

        //when
        userService.add(user);

        //then
        verify(repository).create(user);
    }

    @Test
    void shouldDeleteUser() {
        User user = aUser().build();

        //when
        userService.deleteUser(user.id());

        //then
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
        userService.update(user);

        //then
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
        Throwable throwable = catchThrowable(() -> userService.add(userB));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(DuplicateRecordException.class);
        verify(repository, times(2)).findUserBy(userA.getUsername());
    }
}