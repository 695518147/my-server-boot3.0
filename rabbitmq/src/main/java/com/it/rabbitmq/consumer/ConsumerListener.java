package com.it.rabbitmq.consumer;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerListener {

    @Resource
    private Environment env;

//    @RabbitListener(queues = "directQueue1")
//    public void handleMessage1(String message) throws InterruptedException {
//        Thread.sleep(10000);
//        log.info("handleMessage1: {}, {}" , message, Thread.currentThread().getName());
//    }

//    @RabbitListener(queues = "directQueue2")
//    public void handleMessage2(String message)throws InterruptedException {
//        Thread.sleep(10000);
//        log.info("handleMessage2: {}, {}" , message, Thread.currentThread().getName());
//    }

//    @RabbitListener(queues = "fanoutQueue1")
//    public void handleMessage3(String message)throws InterruptedException {
//        Thread.sleep(1000);
//        log.info("handleMessage3: {}, {}, {}",env.getProperty("server.port") , message, Thread.currentThread().getName());
//    }

//    @RabbitListener(queues = "fanoutQueue2")
//    public void handleMessage4(String message)throws InterruptedException {
//        Thread.sleep(10000);
//        log.info("handleMessage3: {}, {}" , message, Thread.currentThread().getName());
//    }
}
