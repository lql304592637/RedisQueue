package com.sophlean.rmes.test.RedisMessage;

import com.sophlean.core.util.redis.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by lql on 2016/5/12.
 */
public class Consumer {

    public static void sub(JedisPubSub listener, String channel) {
        Jedis jedis = JedisUtils.getJedis();
        jedis.subscribe(listener, channel);
        JedisUtils.returnResource(jedis);
    }

    public static void unSub(JedisPubSub listener, String channel) {
        listener.unsubscribe(channel);
    }
}
