package redis.operations;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

public class LoginAndCookieCaching implements WithRedisContainer {
    private static final long LIMIT = 100;

    public static void main(String[] args) {
        REDIS.start();
        JedisPool jedisPool = new JedisPool(REDIS.getHost(), REDIS.getMappedPort(REDIS.getExposedPorts().get(0)));

        checkToken(jedisPool, "tokenA");
        updateToken(jedisPool, "tokenA", "userIdA", "itemA");
        updateToken(jedisPool, "tokenB", "userIdB", "itemA");
        updateToken(jedisPool, "tokenC", "userIdC", "itemA");
        updateToken(jedisPool, "tokenA", "userIdA", "itemA");

        try (Jedis jedis = jedisPool.getResource()) {
            System.out.println(jedis.zpopmax("recent:")); //Should return most recent token
        }

        REDIS.stop();
    }

    private static String checkToken(JedisPool jedisPool, String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.hget("login:", token);
        }
    }

    private static void updateToken(JedisPool jedisPool, String token, String userId, String item) {
        long timestamp = System.nanoTime();
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.hset("login:", token, userId); //Mapping from token to logged in user
            //Record the current timestamp for the token in the HSET of recent users.
            jedis.zadd("recent:", timestamp, token); //Record when the token was last seen

            if (item != null) {
                /*
                 If the user was viewing an item, we also add the item to the user’s recently viewed ZSET and trim
                 that ZSET if it grows past 25 items.
                 */
                jedis.zadd("viewed:" + token, timestamp, item);
                jedis.zremrangeByRank("viewed:" + token, 0, -26);
            }
        }
    }

    /*
        Should be able to use expiring keys instead of a separate cleanup function.
     */
    private static void cleanUpSessions(JedisPool jedisPool) throws InterruptedException {
        /*
        Only keep the most recent 10 million sessions.
        We’ll fetch the size of the ZSET in a loop.

        If the ZSET is too large, we’ll fetch the oldest items up to 100 at a time
        (because we’re using timestamps, this is just the first 100 items in the ZSET), remove them from the recent ZSET,
        delete the login tokens from the login HASH, and delete the relevant viewed ZSETs.
        */
        try (Jedis jedis = jedisPool.getResource()) {
            while (true) {
                long recentSessionSize = jedis.zcard("recent:");
                if (recentSessionSize <= LIMIT) {
                    Thread.sleep(1);
                    continue;
                }

                long minFetchSize = Math.min(recentSessionSize - LIMIT, 100);
                //Fetch token Ids to remove
                List<String> tokens = jedis.zrange("recent:", 0, minFetchSize - 1);
                //Prepare key names to be deleted
                List<String> tokensToMarkViewed = tokens.stream().map(t -> "viewed:" + t).toList();

                //Remove oldest tokens
                jedis.del(tokensToMarkViewed.toArray(new String[0]));

                String[] tokensAsArray = tokens.toArray(new String[0]);
                jedis.hdel("login:", tokensAsArray);
                jedis.hdel("recent:", tokensAsArray);
                break;
            }
        }
    }


}
