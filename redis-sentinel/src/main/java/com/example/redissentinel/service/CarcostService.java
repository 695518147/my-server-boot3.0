package com.example.redissentinel.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CarcostService {


    @Resource
    private RedisTemplate redisTemplate;

    @Transactional
    public void test(){
        Long test = redisTemplate.opsForValue().increment("test");
        log.info(String.valueOf(test));
    }

}
