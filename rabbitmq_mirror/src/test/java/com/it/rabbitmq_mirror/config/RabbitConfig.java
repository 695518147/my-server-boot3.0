package com.it.rabbitmq_mirror.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kuangbendewoniu
 * @description: TODO
 * @date 2023/6/10 04:21
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue directQueue(){
        return QueueBuilder.durable("directQueue").build();
    }

    @Bean
    public Queue fanoutQueue(){
        return QueueBuilder.durable("fanoutQueue").build();
    }

    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange("directExchange").durable(true).build();
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return ExchangeBuilder.fanoutExchange("fanoutExchange").durable(true).build();
    }

    @Bean
    public Binding bindingDirect(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directQueue");
    }

    @Bean
    public Binding bindingFanout(){
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }


}
