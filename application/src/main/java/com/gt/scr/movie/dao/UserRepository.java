package com.gt.scr.movie.dao;

import com.gt.scr.movie.service.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User findUserBy(UUID userId);

    User findUserBy(String userName);

    List<User> getAllUsers();

    void delete(UUID userId);

    void update(User user);
}
