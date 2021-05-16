package com.toptal.scr.tz.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toptal.scr.tz.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public User findUserBy(UUID userId) {
        Optional<Object> optional = Optional.ofNullable(redisTemplate.opsForHash().get("user", userId));
        return (User) optional.orElse(null);
    }

    @Override
    public User findUserBy(String userName) {
        Optional<Object> userToUserId = Optional.ofNullable(redisTemplate.opsForHash().get("userToUserId", userName));
        return userToUserId.map(userId -> findUserBy((UUID)userId)).orElse(null);
    }

    @Override
    public void add(User user) {
        redisTemplate.opsForHash().put("user", user.id(), user);
        redisTemplate.opsForHash().put("userToUserId", user.getUsername(), user.id());
    }

    @Override
    public List<User> getAllUsers() {
        Map<Object, Object> userEntries = redisTemplate.opsForHash().entries("user");
        return userEntries.values()
                .stream().map(entry -> (User)entry)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID userId) {
        redisTemplate.opsForHash().delete("user", userId);
    }

    @Override
    public void update(User user) {
        redisTemplate.opsForHash().put("user", user.id(), user);
    }
}
