package com.toptal.scr.tz.service;

import com.toptal.scr.tz.dao.UserRepository;
import com.toptal.scr.tz.service.domain.User;
import com.toptal.scr.tz.util.TestBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository repository;

    @Test
    void shouldAddUser() {
        User user = TestBuilders.aUser();

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
        userService.loadUserByUsername(user);

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
}