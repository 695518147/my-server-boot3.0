package com.it.cacheredis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author kuangbendewoniu
 * @version 1.0
 * @description: TODO
 * @date 2023/6/7 21:58
 */
@Component
@PropertySource(value = "classpath:cache-name.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "cache")
@Data
public class RedisCacheNameProperties {

    /**
    * @Description: 缓存ttl配置
    * @Param:
    * @return:
    * @Author:
    * @Date:
    */
    private Map<String, String> cacheNameConfigurations;



}
