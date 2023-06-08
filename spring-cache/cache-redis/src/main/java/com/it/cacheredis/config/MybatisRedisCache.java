package com.it.cacheredis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author kuangbendewoniu
 * @version 1.0
 * @description: TODO
 * @date 2023/6/8 05:51
 */
@Slf4j
public class MybatisRedisCache implements Cache {
    private static final String COMMON_CACHE_KEY = "mybatis";
    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private RedisTemplate<String, Object> redisTemplate;
    private String id;


    public MybatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        redisTemplate = SpringUtils.getBean("redisTemplate");
        this.id = id;
    }

    private RedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = SpringUtils.getBean("redisTemplate");
        }
        return redisTemplate;
    }

    @Override
    public String getId() {
        return this.id;
    }

    private String getKeys() {

        return COMMON_CACHE_KEY + "::" + id + "::*";
    }

    private String getKey(Object key) {
        return COMMON_CACHE_KEY + "::" + id + "::" + DigestUtils.md5DigestAsHex(String.valueOf(key).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void putObject(Object key, Object value) {
        redisTemplate.opsForValue().set(getKey(key), value, 10, TimeUnit.MINUTES);
    }

    @Override
    public Object getObject(Object key) {
        try {
            return redisTemplate.opsForValue().get(getKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("缓存出错 ");
        }
        return null;
    }

    @Override
    public Object removeObject(Object o) {
        Object n = redisTemplate.opsForValue().get(getKey(o));
        redisTemplate.delete(getKey(o));
        return n;
    }

    @Override
    public void clear() {
        Set<String> keys = redisTemplate.keys(getKeys());
        if (!CollectionUtils.isEmpty(keys)) {
            assert keys != null;
            redisTemplate.delete(keys);
        }
    }

    @Override
    public int getSize() {
        Set<String> keys = redisTemplate.keys(getKeys());
        if (!CollectionUtils.isEmpty(keys)) {
            assert keys != null;
            return keys.size();
        }
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}

