package com.toptal.scr.tz.service;

import com.toptal.scr.tz.dao.UserRepository;
import com.toptal.scr.tz.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void add(User user) {
        userRepository.update(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.delete(userId);
    }

    @Override
    public User loadUserByUsername(String userName) {
        return userRepository.findUserBy(userName);
    }

    @Override
    public User findUserBy(UUID userId) {
        return userRepository.findUserBy(userId);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }
}
