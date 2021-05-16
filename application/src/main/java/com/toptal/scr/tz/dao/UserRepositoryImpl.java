package com.toptal.scr.tz.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.scr.tz.service.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public User findUserBy(String userName) {
        Optional<Object> optional = Optional.ofNullable(redisTemplate.opsForHash().get("user", userName));
        return (User) optional.orElse(null);
    }

    @Override
    public void add(User user) {
        redisTemplate.opsForHash().put("user", user.getUsername(), user);
    }

    @Override
    public List<User> getAllUsers() {
        Map<Object, Object> userEntries = redisTemplate.opsForHash().entries("user");
        return userEntries.values()
                .stream().map(entry -> (User)entry)
                .collect(Collectors.toList());
    }
}
