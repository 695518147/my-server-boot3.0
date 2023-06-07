package com.example.rediscluster;

import com.example.rediscluster.entity.Message;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StopWatch;

import java.security.SecureRandom;
import java.util.UUID;

@SpringBootTest
@Slf4j
class RedisClusterApplicationTests {
    @Resource
    private RedissonClient redisson;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void contextLoads() {
    }

    @Test
    public void test() throws InterruptedException {

        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        for (long i = 0; i < 2l; i++) {
            String key = randomString(3);
            SecureRandom random = new SecureRandom();
            String field = UUID.randomUUID().toString();
            int score = random.nextInt(100000);
            try {
                redisTemplate.opsForValue().set("message", new Message(1, "message"));
                Message message = (Message) redisTemplate.opsForValue().get("message");
                log.info("Message: " + message);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            log.info("{}: {}: {}", key, field, score);
        }
        stopwatch.stop();
        System.out.println("cluster total times：" + stopwatch.getTotalTimeMillis());

    }

    public static String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
