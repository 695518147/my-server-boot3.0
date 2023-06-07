package com.example.rediscluster;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis发布订阅
 */
@SpringBootTest
public class RedisPubTestRedis {

    /**
     * 订单支付完成主题
     */
    private static final String ORDER_PAY_SUCCESS = "ORDER-PAY-SUCCESS";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 模拟发送5调订单支付完成的消息
     */
    @Test
    public void sendMessage(){
        for (int i = 0; i < 5; i++) {
            stringRedisTemplate.convertAndSend(ORDER_PAY_SUCCESS,"DD" + System.currentTimeMillis());
        }
    }
}
