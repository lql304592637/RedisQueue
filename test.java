package com.sophlean.rmes.test.RedisMessage;

/**
 * Created by lql on 2016/5/12.
 */
public class test {
    public static void main(String[] args) {
        String channel = "testChannel";
        RedisPersistMessageListener listener = new RedisPersistMessageListener();
        Consumer.sub(listener, channel);
    }
}
