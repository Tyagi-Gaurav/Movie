package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.UserRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier(value = "mysql")
    private UserRepository userRepository;

    @Override
    public void add(User user) {
        try {
            userRepository.findUserBy(user.getUsername());
            throw new DuplicateRecordException("User already exists.");
        } catch (IllegalStateException ignored) {
           //User Not found. Carry on.
        }

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
