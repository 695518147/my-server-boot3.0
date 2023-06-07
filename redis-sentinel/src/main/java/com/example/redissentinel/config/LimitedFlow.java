package com.example.redissentinel.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.UUID;

@Configuration
@Slf4j
public class LimitedFlow {

    @Resource
    private RedisTemplate redisTemplate;

    public boolean getToken(){
        Object token = redisTemplate.opsForList().leftPop("limit_flow");
        log.info("获取token：{}", token);
        return Objects.nonNull(token);

    }

    public void addToken(){
        redisTemplate.opsForList().rightPush("limit_flow", UUID.randomUUID().toString());
    }

}
