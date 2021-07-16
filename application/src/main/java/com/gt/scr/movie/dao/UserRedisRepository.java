package com.gt.scr.movie.dao;

import com.gt.scr.movie.config.DatabaseConfig;
import com.gt.scr.movie.service.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRedisRepository implements UserRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private DatabaseConfig databaseConfig;

    @Override
    public Optional<User> findUserBy(UUID userId) {
        Optional<Object> optional = Optional.ofNullable(redisTemplate.opsForHash().get("user", userId));
        return optional.map(User.class::cast);
    }

    @Override
    public Optional<User> findUserBy(String userName) {
        Optional<Object> userToUserId = Optional.ofNullable(redisTemplate.opsForHash().get("userToUserId", userName));
        return userToUserId.flatMap(userId -> findUserBy((UUID) userId));
    }

    @Override
    public List<User> getAllUsers() {
        Map<Object, Object> userEntries = redisTemplate.opsForHash().entries("user");
        return userEntries.values()
                .stream().map(User.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID userId) {
        redisTemplate.opsForHash().delete("user", userId);
    }

    @Override
    public void update(User user) {
        var key = String.format("%s-%s", user.id(), user.getUsername());
        if (redisTemplate.opsForValue().get(key) == null) {
            redisTemplate.opsForValue().set(key, user, databaseConfig.duplicateInterval());
            redisTemplate.opsForHash().put("user", user.id(), user);
            redisTemplate.opsForHash().put("userToUserId", user.getUsername(), user.id());
        }
    }
}
