package com.it.cacheredis.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

/**
 * @author kuangbendewoniu
 * @version 1.0
 * @description: TODO ehcache配置类
 * @date 2023/6/7 17:26
 */
@EnableCaching
@Configuration
@Slf4j
public class RedisCacheConfig {

    // ${cache} 获取配置文件的配置信息   #{}是spring表达式，获取Bean对象的属性
    @Value("#{${cache.ttl}}")
    private Map<String, Long> ttlParams;
    @Value("${cache.env}")
    private String env;

    /**
     * @param redisConnectionFactory
     * @功能描述 redis作为缓存时配置缓存管理器CacheManager，主要配置序列化方式、自定义
     * <p>
     * 注意：配置缓存管理器CacheManager有两种方式：
     * 方式1：通过RedisCacheConfiguration.defaultCacheConfig()获取到默认的RedisCacheConfiguration对象，
     * 修改RedisCacheConfiguration对象的序列化方式等参数【这里就采用的这种方式】
     * 方式2：通过继承CachingConfigurerSupport类自定义缓存管理器，覆写各方法，参考：
     * <p>
     * 切记：在缓存配置类中配置以后，yaml配置文件中关于缓存的redis配置就不会生效，如果需要相关配置需要通过@value去读取
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheNameProperties redisCacheNameProperties) {
        String prefix = "redisCacheDemo_";
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration
                // 设置key采用String的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8))
                //设置value序列化方式采用jackson方式序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                //当value为null时不进行缓存
                .disableCachingNullValues()
                // 配置缓存空间名称的前缀
                .prefixCacheNameWith(prefix)
                //全局配置缓存过期时间【可以不配置】
                .entryTtl(Duration.ofMinutes(30L));
        //专门指定某些缓存空间的配置，如果过期时间【主要这里的key为缓存空间名称】
        Map<String, RedisCacheConfiguration> map = new HashMap<>();
        //指定特定缓存空间对应的过期时间
        RedisCacheConfiguration finalRedisCacheConfiguration = redisCacheConfiguration;
        Optional.of(redisCacheNameProperties.getCacheNameConfigurations()).ifPresent(config -> {
            for (Map.Entry<String, String> entry : config.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                map.put(key, finalRedisCacheConfiguration.entryTtl(Duration.ofSeconds(compute(value))));
            }
        });
        map.put("userCacheName", finalRedisCacheConfiguration.entryTtl(Duration.ofMinutes(10)));
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)  //默认配置
                // 设置初始化缓存空间
                .initialCacheNames(map.keySet())
                // 加载配置
                .withInitialCacheConfigurations(map)  //某些缓存空间的特定配置
                .build();
    }

    public Long compute(String time){
        if (StringUtils.isNumeric(time)) {
            return Long.valueOf(time);
        }

        if (StringUtils.contains(time,"*")) {
            String[] parts = StringUtils.split(time, "\\*");
            Long num = 1l;
            for (String part : parts) {
                if (StringUtils.isNumeric(part.trim())) {
                    num *= Long.valueOf(part.trim());
                }
            }
            return num;
        }

        throw new IllegalArgumentException("Invalid cache-name");
    }

//    @Bean
//    public CacheManager cacheManager() {
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                // 设置默认缓存有效期
//                .entryTtl(Duration.ofMinutes(30));
//        return new MyRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
//                RedisCacheConfiguration.defaultCacheConfig());
//    }

    /**
     * 自定义缓存数据 key 生成策略
     * target: 类
     * method: 方法
     * params: 参数
     *
     * @return KeyGenerator
     * 注意: 该方法只是声明了key的生成策略,还未被使用,需在@Cacheable注解中指定keyGenerator
     * 如: @Cacheable(value = "key", keyGenerator = "keyGenerator")
     */
    @Primary
    @Bean
    public KeyGenerator keyGenerator() {
        //new了一个KeyGenerator对象,采用lambda表达式写法
        //类名+方法名+参数列表的类型+参数值，然后再做md5转16进制作为key
        //使用冒号(:)进行分割，可以很多显示出层级关系
        return (target, method, params) -> {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append(target.getClass().getName());
            strBuilder.append(":");
            strBuilder.append(method.getName());
            for (Object obj : params) {
                if (obj != null) {
                    strBuilder.append(":");
                    strBuilder.append(obj.getClass().getName());
                    strBuilder.append(":");
                    strBuilder.append(JSON.toJSONString(obj));
                }
            }
            if (StringUtils.endsWithIgnoreCase(env, "dev")) {
                return strBuilder;
            }
            //log.info("ehcache key str: " + strBuilder.toString());
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(strBuilder.toString().getBytes(StandardCharsets.UTF_8));
            log.info("redis key md5DigestAsHex: " + md5DigestAsHex);
            return md5DigestAsHex;
        };
    }

}
