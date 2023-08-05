package redis.operations;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ShoppingCartCaching implements WithRedisContainer {
    public static void main(String[] args) {
        REDIS.start();
        JedisPool jedisPool = new JedisPool(REDIS.getHost(), REDIS.getMappedPort(REDIS.getExposedPorts().get(0)));

        try (Jedis jedis = jedisPool.getResource()) {
            addToCart(jedis, "userIdA", "itemA", 1);
            addToCart(jedis, "userIdB", "itemA", 2);
            addToCart(jedis, "userIdC", "itemA", 3);
            addToCart(jedis, "userIdA", "itemA", 4);


            System.out.println(jedis.zpopmax("recent:")); //Should return most recent token
        }

        REDIS.stop();
    }

    private static void addToCart(Jedis jedis, String session, String item, int count) {
        if (count <= 0) {
            jedis.hdel("cart:" + session, item);
        } else {
            jedis.hset("cart:" + session, item, String.valueOf(count));
        }
    }
}
