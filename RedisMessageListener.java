package com.sophlean.rmes.test.RedisMessage;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by lql on 2016/5/13.
 */
public class RedisMessageListener extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        new Thread() {
            public void run() {
                executeMessage(message);
            }
        }.start();
    }

    @Override
    public void onPMessage(String s, String s1, String s2) {

    }

    @Override
    public void onSubscribe(String channel, int i) {
        System.out.println("初始化订阅");
    }

    @Override
    public void onUnsubscribe(String channel, int i) {
        System.out.println("取消了订阅");
    }

    @Override
    public void onPUnsubscribe(String s, int i) {

    }

    @Override
    public void onPSubscribe(String s, int i) {

    }

    public void executeMessage(String message) {
        System.out.println(message);
    }
}
