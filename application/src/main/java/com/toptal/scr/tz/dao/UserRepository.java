package com.toptal.scr.tz.dao;

import com.toptal.scr.tz.service.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User findUserBy(UUID userId);

    User findUserBy(String userName);

    void add(User user);

    List<User> getAllUsers();

    void delete(UUID userId);

    void update(User user);
}
