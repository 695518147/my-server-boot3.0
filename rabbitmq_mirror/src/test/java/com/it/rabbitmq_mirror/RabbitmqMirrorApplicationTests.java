package com.it.rabbitmq_mirror;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqMirrorApplicationTests {

	@Resource
	private RabbitTemplate rabbitTemplate;

	@Test
	void contextLoads() {

		rabbitTemplate.convertAndSend("amq.direct", "testdirect", "这是一个测试消息");
	}

}
