package com.example.redissentinel;

import com.example.redissentinel.service.CarcostService;
import com.github.javafaker.Faker;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.security.SecureRandom;
import java.time.Duration;

@SpringBootTest
class RedisSentinelApplicationTests {

    @Resource
    //操作字符串的template，StringRedisTemplate是RedisTemplate的一个子集
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    // RedisTemplate，可以进行所有的操作
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private CarcostService carcostService;

    @Test
    void read() {
        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForValue().set("test" + i, i, Duration.ofSeconds(20 + i));
        }
    }

    @Test
    void contextLoads() {

        carcostService.test();

    }

    @Test
    public void test() throws InterruptedException {
        com.github.javafaker.Book fakerBook = Faker.instance().book();
        System.out.println(fakerBook.publisher());

//        StopWatch stopwatch = new StopWatch();
//        stopwatch.start();
//        for (int i = 0; i < 100000; i++) {
//            Map<String, String> map = new HashMap<>();
//            String field = randomString(5);
//            map.put(field, randomString(22));
//            String key = randomString(12);
//            redisTemplate.opsForHash().putAll(key, map);
//        }
//        stopwatch.stop();
//        System.out.println("sentinel total times：" + stopwatch.getTotalTimeMillis());

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
