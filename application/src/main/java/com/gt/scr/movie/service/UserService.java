package com.gt.scr.movie.service;


import com.gt.scr.movie.service.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    void add(User user);

    List<User> getAllUsers();

    void deleteUser(UUID userId);

    User loadUserByUsername(String username);

    User findUserBy(UUID userId);

    void update(User user);
}
