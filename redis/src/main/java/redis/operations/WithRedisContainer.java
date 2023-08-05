package redis.operations;

import com.redis.testcontainers.RedisContainer;

public interface WithRedisContainer {
    RedisContainer REDIS = new RedisContainer(
            RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG)).withKeyspaceNotifications();

}
