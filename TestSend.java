package com.sophlean.rmes.test.RedisMessage;

/**
 * Created by lql on 2016/5/12.
 */
public class TestSend {
    public static void main(String[] args) {
        Producer producer = new Producer();
        String channel = "testChannel";
        String message = "{\"name\":\"112233\"}";
        int i = 0;
        while(i != 10) {
            ++i;
            new Thread() {
                public void run() {
                    int j = 0;
                    while(true) {
                        producer.sendPersistMessage(channel, message + ++j);
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }.start();
        }
    }
}
