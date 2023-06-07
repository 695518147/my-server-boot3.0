package com.it.cacheredis.config;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * @author kuangbendewoniu
 * @version 1.0
 * @description: TODO
 * @date 2023/6/8 03:17
 */

public class MyRedisCacheManager extends RedisCacheManager {

    public MyRedisCacheManager(RedisCacheWriter cacheWriter,
                               RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        if (array.length > 1) { // 解析TTL
            // 例如 12 默认12秒， 12d=12天
            String ttlStr = array[1];
            Duration duration = convertDuration(ttlStr);
            cacheConfig = cacheConfig.entryTtl(duration);
        }
        return super.createRedisCache(name, cacheConfig);
    }

    private Duration convertDuration(String ttlStr) {
        if (org.apache.commons.lang3.StringUtils.isNumeric(ttlStr)) {
            return Duration.ofSeconds(Long.parseLong(ttlStr));
        }

        ttlStr = ttlStr.toUpperCase();

        if (ttlStr.lastIndexOf("D") != -1) {
            return Duration.parse("P" + ttlStr);
        }

        return Duration.parse("PT" + ttlStr);
    }

}

