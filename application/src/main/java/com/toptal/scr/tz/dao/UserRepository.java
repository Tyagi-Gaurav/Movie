package com.toptal.scr.tz.dao;

import com.toptal.scr.tz.service.domain.User;

import java.util.List;

public interface UserRepository {
    User findUserBy(String userName);

    void add(User user);

    List<User> getAllUsers();
}
