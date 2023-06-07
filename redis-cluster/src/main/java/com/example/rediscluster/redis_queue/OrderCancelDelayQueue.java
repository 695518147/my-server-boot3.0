package com.example.rediscluster.redis_queue;

import com.alibaba.fastjson.JSONObject;
import com.example.rediscluster.entity.Message;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * redis zset 实现的延迟队列
 */
@Slf4j
@Component
public class OrderCancelDelayQueue {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * 延迟队列名称，可以根据不通的业务处理设置不同的队列
     */
    private static final String DELAY_QUEUE_NAME = "cancelOrderQueue";

    /**
     * 发送数据
     *
     */
    public Boolean pushMessage(Message message, int delay) {
        long score = System.currentTimeMillis() + delay * 1000;
        String msg = JSONObject.toJSONString(message);
        Boolean add = stringRedisTemplate.opsForZSet().add(DELAY_QUEUE_NAME, msg, score);
        return add;
    }


    public List<Message> pull() {

        List<Message> msgList = new ArrayList<>();
        try {
            Set<String> strings = stringRedisTemplate.opsForZSet().rangeByScore(DELAY_QUEUE_NAME, 0, System.currentTimeMillis());
            if (strings == null) {
                return Collections.emptyList();
            }
            msgList = strings.stream()
                    .map(msg -> {
                        Message message = null;
                        try {
                            message = JSONObject.parseObject(msg, Message.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return message;
                    }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.toString());
        }
        return msgList;
    }

    /**
     * 移除消息
     */
    public Boolean remove(Message message) {
        Long remove = stringRedisTemplate.opsForZSet().remove(DELAY_QUEUE_NAME, JSONObject.toJSONString(message));
        return remove > 0;
    }
}
