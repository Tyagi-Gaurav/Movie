package com.gt.scr.movie.service;

import com.gt.scr.movie.dao.UserRepository;
import com.gt.scr.movie.exception.DuplicateRecordException;
import com.gt.scr.movie.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier(value = "mysql")
    private UserRepository userRepository;

    @Override
    public void add(User user) {
        Optional<User> userBy = userRepository.findUserBy(user.getUsername());
        if (userBy.isPresent()) {
            throw new DuplicateRecordException("User already exists.");
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
    public Optional<User> loadUserBy(String userName) {
        return userRepository.findUserBy(userName);
    }

    @Override
    public User findUserBy(UUID userId) {
        Optional<User> userBy = userRepository.findUserBy(userId);
        return userBy.orElse(null);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.loadUserBy(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("Unable to find User: " + username));
    }
}
