package com.example.redissentinel.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Carcost) API
 *
 * @author yinian
 * @since 2023-06-03 19:58:35
 */
@RestController
@RequestMapping()
@Slf4j
public class TestController {

    @GetMapping("test")
    public String test(@RequestParam String param) throws InterruptedException {
        log.info("Test {}", param);
        Thread.sleep(300);
        return param;
    }

}

