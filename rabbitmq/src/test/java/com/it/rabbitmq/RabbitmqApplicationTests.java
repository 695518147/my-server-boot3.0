package com.it.rabbitmq;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqApplicationTests {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {

        for (int i = 0; i < 1000; i++) {
//            rabbitTemplate.convertAndSend("directExchange", "directQueue1", "directQueue1");
//            rabbitTemplate.convertAndSend("directExchange", "directQueue2", "directQueue2");



            rabbitTemplate.convertAndSend("fanoutExchange", null, "fanoutQueue1: " + i);
//            rabbitTemplate.convertAndSend("fanoutExchange",  null,"fanoutQueue2");
        }

    }


}
