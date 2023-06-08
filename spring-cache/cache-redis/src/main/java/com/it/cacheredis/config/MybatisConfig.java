package com.it.cacheredis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author kuangbendewoniu
 * @version 1.0
 * @description: TODO
 * @date 2023/6/8 06:09
 */
@MapperScan("com.it.cacheredis.dao")
@Configuration
public class MybatisConfig {
}
