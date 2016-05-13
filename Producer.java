package com.sophlean.rmes.test.RedisMessage;

import com.sophlean.core.util.redis.JedisUtils;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

/**
 * Created by lql on 2016/5/12.
 */
public class Producer {

    public void sendPersistMessage(String channel, String message) {
        Jedis jedis = JedisUtils.getJedis();
        message = produceMessage(message);
        jedis.publish(channel, message);
        Set<String> curChannel = jedis.smembers(channel);
        for(String curSub : curChannel) {
            jedis.sadd(curSub, message);
        }
        JedisUtils.returnResource(jedis);
    }

    private String produceMessage(String message) {
        String preMessage = UUID.randomUUID().toString();
        return preMessage + "/" + message;
    }

    public void sendMessage(String channel, String message) {
        Jedis jedis = JedisUtils.getJedis();
        jedis.publish(channel, message);
        JedisUtils.returnResource(jedis);
    }
}
