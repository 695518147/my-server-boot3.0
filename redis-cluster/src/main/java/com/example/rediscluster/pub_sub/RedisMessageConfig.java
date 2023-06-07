package com.example.rediscluster.pub_sub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 发布订阅
 */
@Configuration
public class RedisMessageConfig {

    /**
     * 监听订单支付完成主题
     */
    private static final String ORDER_PAY_SUCCESS = "ORDER-PAY-SUCCESS";

    /**
     * 注入消息监听适配器
     */
    @Bean
    public MessageListenerAdapter messageListenerAdapter(MessageListener messageListener){
        return new MessageListenerAdapter(messageListener, "onMessage");
    }

    /**
     * 注入消息监听容器
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter){
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);

        //订阅订单支付成功主题
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic(ORDER_PAY_SUCCESS));
        return redisMessageListenerContainer;
    }

}

