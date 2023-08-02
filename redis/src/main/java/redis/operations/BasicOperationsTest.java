package redis.operations;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.*;

public class BasicOperationsTest implements WithRedisContainer {
    public static void main(String[] args) {
        REDIS.start();
        out.println("Host: " + REDIS.getHost());
        out.println("Exposed Port: " + REDIS.getExposedPorts());
        Integer mappedPort = REDIS.getMappedPort(REDIS.getExposedPorts().get(0));
        out.println("Mapped Port: " + mappedPort);

        JedisPool pool = new JedisPool(REDIS.getHost(), mappedPort);

        try (Jedis jedis = pool.getResource()) {
            jedis.set("foo", "bar");

            out.println(jedis.get("foo")); // prints bar

            // Store & Retrieve a HashMap
            Map<String, String> hash = new HashMap<>();;
            hash.put("name", "John");
            hash.put("surname", "Smith");
            hash.put("company", "Redis");
            hash.put("age", "29");

            jedis.hset("user-session:123", hash);

            out.println(jedis.hgetAll("user-session:123"));
            out.println(jedis.hget("user-session:123", "name"));
        }

        REDIS.stop();
    }
}
