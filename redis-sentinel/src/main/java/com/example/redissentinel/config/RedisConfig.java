package com.example.redissentinel.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import io.lettuce.core.ReadFrom;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

/**
 * RedisConfig 类用于配置 Redis 连接和序列化设置。
 */
@Configuration
@ConfigurationProperties("spring.data.redis.sentinel")
@Getter
@Setter
@Slf4j
public class RedisConfig {
    private String master;
    private String password;
    private Set<String> nodes;

    /**
     * 配置 Lettuce 客户端从 Redis 副本节点读取数据。
     */
    @Bean
    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigurationBuilderCustomizer() {
        return builder -> builder.readFrom(ReadFrom.REPLICA);
    }

    /**
     * 配置 RedisTemplate 以使用自定义序列化方式。
     *
     * @param redisConnectionFactory Redis 连接工厂
     * @return 配置好的 RedisTemplate 对象
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 使用 Jackson2JsonRedisSerializer 进行序列化和反序列化
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL);
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用 StringRedisSerializer 序列化和反序列化 key 值
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // 使用 json 序列化 value 值
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 配置 RedisMessageListenerContainer 以监听 Redis 消息。
     *
     * @param connectionFactory Redis 连接工厂
     * @return 配置好的 RedisMessageListenerContainer 对象
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    /**
     * 配置 KeyExpirationEventMessageListener 以监听 Redis 键过期事件。
     *
     * @param listenerContainer RedisMessageListenerContainer 对象
     * @return 配置好的 KeyExpirationEventMessageListener 对象
     */
    @Bean
    public KeyExpirationEventMessageListener keyExpirationEventMessageListener(RedisMessageListenerContainer listenerContainer){
        return new KeyExpirationEventMessageListener(listenerContainer){
            @Override
            public void onMessage(Message message, byte[] pattern) {
                // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
                String expiredKey = message.toString();
                log.info("{}: 已经过期", expiredKey);
                String SystemCancelTime = "SystemCancelTime";
                if(expiredKey.contains(SystemCancelTime)){
                    //处理业务
                }
            }
        };
    }
}