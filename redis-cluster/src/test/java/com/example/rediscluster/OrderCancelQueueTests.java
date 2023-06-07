package com.example.rediscluster;

import com.alibaba.fastjson.JSONObject;
import com.example.rediscluster.entity.Message;
import com.example.rediscluster.redis_queue.OrderCancelDelayQueue;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class OrderCancelQueueTests {
    
    @Resource
    private OrderCancelDelayQueue orderCancelDelayQueue;
    
    @Test
    public void pushMessage() throws InterruptedException {
        for (int i = 0; i < 100000; i++) {
            Message message = new Message(i, "message test: ");
            orderCancelDelayQueue.pushMessage(message,10);
            Thread.sleep(1000);
            log.warn("publish message: {}", JSONObject.toJSONString(message));
        }
    }
}
