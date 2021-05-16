package com.toptal.scr.tz.service;

import com.toptal.scr.tz.dao.UserRepository;
import com.toptal.scr.tz.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String userName) {
        return userRepository.findUserBy(userName);
    }

    @Override
    public void add(User user) {
        userRepository.add(user);
    }
}
