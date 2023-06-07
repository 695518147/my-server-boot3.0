package com.it.rabbitmq.consumer;

import org.springframework.amqp.core.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig implements CommandLineRunner {


    @Bean
    public Queue directQueue1() {
        return new Queue("directQueue1");
    }


    @Bean
    public Queue directQueue2() {
        return new Queue("directQueue2");
    }

    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanoutQueue1");
    }


    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanoutQueue2");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }


    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder
                .bind(directQueue1())
                .to(directExchange())
                .with("directQueue1");
    }
    @Bean
    public Binding binding2() {
        return BindingBuilder
                .bind(directQueue2())
                .to(directExchange())
                .with("directQueue2");
    }
    @Bean
    public Binding binding3() {
        return BindingBuilder
                .bind(fanoutQueue1())
                .to(fanoutExchange());
    }
    @Bean
    public Binding binding4() {
        return BindingBuilder
                .bind(fanoutQueue2())
                .to(fanoutExchange());
    }



    @Override
    public void run(String... args) throws Exception {
        BindingBuilder.bind(directQueue1())
                .to(directExchange())
                .with("directQueue1");
        BindingBuilder.bind(directQueue2())
                .to(directExchange())
                .with("directQueue2");

        BindingBuilder.bind(fanoutQueue1())
                .to(fanoutExchange());

        BindingBuilder.bind(fanoutQueue2())
                .to(fanoutExchange());
    }
}
