package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.UserRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.User;
import com.gt.scr.movie.util.TestBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.empty;
import static java.util.Optional.of;
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
        User user = TestBuilders.aUser();
        when(repository.findUserBy(user.getUsername())).thenReturn(empty());

        //when
        userService.add(user);

        //then
        verify(repository).update(user);
    }

    @Test
    void shouldDeleteUser() {
        User user = TestBuilders.aUser();

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
        User user = TestBuilders.aUser();

        //when
        userService.update(user);

        //then
        verify(repository).update(user);
    }

    @Test
    void shouldThrowExceptionWhenTryingToAddDuplicateUser() {
        //given
        User userA = TestBuilders.aUser();
        User userB = TestBuilders.aUserWithUserName(userA.getUsername());
        when(repository.findUserBy(userA.getUsername())).thenReturn(Optional.empty()).thenReturn(of(userA));
        userService.add(userA);

        //when
        Throwable throwable = catchThrowable(() -> userService.add(userB));

        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(DuplicateRecordException.class);
        verify(repository, times(2)).findUserBy(userA.getUsername());
    }
}