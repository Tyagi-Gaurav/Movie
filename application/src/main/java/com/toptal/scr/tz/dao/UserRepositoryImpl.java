package com.toptal.scr.tz.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.scr.tz.service.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public User findUserBy(String userName) {
        LOG.info("Finding userName {}", userName);
        Optional<Object> optional = Optional.ofNullable(redisTemplate.opsForValue().get(userName));
        return (User) optional.orElse(null);
    }

    @Override
    public void add(User user) {
        LOG.info("Storing userName {}", user.getUsername());
        redisTemplate.opsForValue().set(user.getUsername(), user);
    }
}
