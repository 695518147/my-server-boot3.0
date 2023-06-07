package com.example.redissentinel.config;

import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {

    @Resource
    private LimitedFlow limitedFlow;

    /**
     * 每10秒执行一次
     */
    @Scheduled(cron = "*/10 * * * * ?")
    private void printNowDate() {
        limitedFlow.addToken();
    }
}
