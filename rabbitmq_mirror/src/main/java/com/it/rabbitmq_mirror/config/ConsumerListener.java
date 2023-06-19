package com.it.rabbitmq_mirror.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author kuangbendewoniu
 * @description: TODO
 * @date 2023/6/10 17:24
 */
@Component
@Slf4j
public class ConsumerListener {

    @RabbitListener(queues = "directQueue")
    public void handlerConsumer(String message) throws InterruptedException {
        log.info("handlerConsumer,{}", message.toString());
        Thread.sleep(5000);
    }

    @RabbitListener(queues = "fanoutQueue")
    public void handlerConsumer1(String message){
        log.info("handlerConsumer1,{}", message.toString());
    }
}
