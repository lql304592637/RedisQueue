package com.sophlean.rmes.test.RedisMessage;

import com.sophlean.core.util.redis.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Set;

/**
 * Created by lql on 2016/5/12.
 */
public class RedisPersistMessageListener extends JedisPubSub {
    private final String used = "used";

    @Override
    public void onMessage(String channel, String message) {
        String msg = getMessage(message);
        new Thread() {
            public void run() {
                executeMessage(message);
            }
        }.start();
        Jedis jedis = JedisUtils.getJedis();
        jedis.sadd(used + this.getClass().getName(), message);
        JedisUtils.returnResource(jedis);
    }

    @Override
    public void onSubscribe(String channel, int i) {
        Jedis jedis = JedisUtils.getJedis();
        boolean exist = jedis.sismember(channel, this.getClass().getName());
        if(!exist) {
            jedis.sadd(channel, this.getClass().getName());
        }
        else {
            Set<String> usedMessageList = jedis.smembers(used + this.getClass().getName());
            if(null != usedMessageList) {
                for(String usedMessgae : usedMessageList) {
                    jedis.srem(this.getClass().getName(), usedMessgae);
                }
                jedis.del(used + this.getClass().getName());
            }
            Set<String> subMessageList = jedis.smembers(this.getClass().getName());
            if(null != subMessageList) {
                for (String message : subMessageList) {
                    String msg = getMessage(message);
                    executeMessage(message);
                    jedis.srem(this.getClass().getName(), message);
                }
            }
        }
        JedisUtils.returnResource(jedis);
    }

    @Override
    public void onUnsubscribe(String channel, int i) {
        Jedis jedis = JedisUtils.getJedis();
        jedis.srem(channel, this.getClass().getName());
        jedis.del(this.getClass().getName());
        jedis.del(used + this.getClass().getName());
        JedisUtils.returnResource(jedis);
        System.out.println("取消了订阅");
    }

    @Override
    public void onPUnsubscribe(String s, int i) {

    }

    @Override
    public void onPMessage(String s, String s1, String s2) {

    }

    @Override
    public void onPSubscribe(String s, int i) {

    }

    public void executeMessage(String message) {
        System.out.println(message);
    }

    private String getMessage(String message) {
        String[] messages = message.split("/");
        return messages[1];
    }
}