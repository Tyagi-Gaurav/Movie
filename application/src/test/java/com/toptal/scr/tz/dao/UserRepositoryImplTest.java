package com.toptal.scr.tz.dao;

import com.toptal.scr.tz.service.domain.User;
import com.toptal.scr.tz.util.TestBuilders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserRepositoryImpl.class)
class UserRepositoryImplTest {
    @Autowired
    private UserRepository userRepository;

    @MockBean
    private RedisTemplate redisTemplate;

    @MockBean
    private HashOperations hashOperations;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    void shouldFindUserByUserId() {
        //given
        User expectedUser = TestBuilders.aUser();
        when(hashOperations.get("user", expectedUser.id())).thenReturn(expectedUser);

        //when
        User user = userRepository.findUserBy(expectedUser.id());

        //then
        assertThat(user).isEqualTo(expectedUser);
    }

    @Test
    void shouldFindUserByUserName() {
        //given
        String userName = "userName";
        User expectedUser = TestBuilders.aUser();
        when(hashOperations.get("user", expectedUser.id())).thenReturn(expectedUser);
        when(hashOperations.get("userToUserId", userName)).thenReturn(expectedUser.id());

        //when
        User user = userRepository.findUserBy(userName);

        //then
        assertThat(user).isEqualTo(expectedUser);
    }

    @Test
    void shouldGetAllUsers() {
        //given
        User expectedUser = TestBuilders.aUser();
        Map<UUID, User> users = Map.of(expectedUser.id(), expectedUser);
        when(hashOperations.entries("user")).thenReturn(users);

        //when
        List<User> allUsers = userRepository.getAllUsers();

        //then
        assertThat(allUsers).isEqualTo(Collections.singletonList(expectedUser));
    }

    @Test
    void shouldDeleteUser() {
        //given
        UUID userId = UUID.randomUUID();

        //when
        userRepository.delete(userId);

        //then
        verify(hashOperations).delete("user", userId);
    }

    @Test
    void shouldUpdateUser() {
        //given
        User user = TestBuilders.aUser();

        //when
        userRepository.update(user);

        //then
        verify(hashOperations).put("user", user.id(), user);
        verify(hashOperations).put("userToUserId", user.getUsername(), user.id());
    }
}