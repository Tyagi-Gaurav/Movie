package com.toptal.scr.tz.service;

import com.toptal.scr.tz.dao.UserRepository;
import com.toptal.scr.tz.service.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User loadUserByUsername(String userName) {
        LOG.info("Get User by username");

//        return ImmutableUser.builder()
//                .username("hello")
//                .password(passwordEncoder.encode("password"))
//                .firstName("firstName")
//                .lastName("lastName")
//                .authorities(Arrays.asList(new SimpleGrantedAuthority("USER")))
//                .isEnabled(true)
//                .isAccountNonExpired(true)
//                .isAccountNonLocked(true)
//                .isCredentialsNonExpired(true)
//                .build();
        return userRepository.findUserBy(userName);
    }

    @Override
    public void add(User user) {
        userRepository.add(user);
    }


}
