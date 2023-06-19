package com.it.rabbitmq_mirror;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
@Slf4j
class RabbitmqMirrorApplicationTests {

	@Resource
	private RabbitTemplate rabbitTemplate;

	@Test
	void contextLoads() {

		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			rabbitTemplate.convertAndSend("directExchange", "directQueue", "这是一个测试消息" + i);
//			rabbitTemplate.convertAndSend("fanoutExchange", null, "这是一个测试消息");
			stopWatch.stop();
			log.info("执行时间:{}", stopWatch.getTotalTimeMillis());
		}

	}

}
