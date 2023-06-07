package com.example.rediscluster.pub_sub;

import org.springframework.stereotype.Component;

/**
 * 处理消息是异步的
 */
@Component
public class MessageListener {

    public void onMessage(String message) throws InterruptedException {
        System.out.println("接收消息：" + message);
        Thread.sleep(10000);
        System.out.println("接收消息1：" + message);
    }
}

