package redis.operations;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.Executors;

public class PublishSubscribeOperations implements WithRedisContainer {
    public static void main(String[] args) throws InterruptedException {
        REDIS.start();
        var publishPool = new JedisPool(REDIS.getHost(), REDIS.getMappedPort(REDIS.getExposedPorts().get(0)));
        var subscriberPool = new JedisPool(REDIS.getHost(), REDIS.getMappedPort(REDIS.getExposedPorts().get(0)));

        try(Jedis publishResource = publishPool.getResource();
        Jedis subscriberResource = subscriberPool.getResource()) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    publishResource.publish("channel", i + "");
                }
                System.out.println("Finish publisher");
            });

            thread.start();
            MySubsriber jedisPubSub = new MySubsriber();
            Executors.newSingleThreadExecutor().execute(() -> subscriberResource.psubscribe(jedisPubSub, "channel"));
            System.out.println("Waiting to finish");
            Thread.sleep(5000);
            jedisPubSub.unsubscribe();
        }

        System.out.println("Stop Redis now");
        REDIS.stop();
    }


    private static final class MySubsriber extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            System.out.println("Received message " + message + " from channel " + channel);
        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            System.out.println("Someone subscribed to channel " + pattern);
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            System.out.println("Received Pmessage " + message + " from channel " + channel);
        }
    }

}
