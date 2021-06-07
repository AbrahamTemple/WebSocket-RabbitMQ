package com.cloud.olifebase.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.16
 * @GitHub https://github.com/AbrahamTemple/
 * @description:
 */
@Configuration
public class RabbitConfig {

    public static final String ADVISORY_EXCHANGE = "advisory_exchange";
    public static final String ADVISORY_QUEUE = "advisory_queue";

    @Bean
    public FanoutExchange advisoryExchange(){
        return new FanoutExchange(ADVISORY_EXCHANGE,true,false);
    }

    @Bean
    public Queue advisoryQueue(){
        return new Queue(ADVISORY_QUEUE,true,false,false);
    }

    @Bean
    public Binding advisoryBinding(){
        return BindingBuilder.bind(advisoryQueue()).to(advisoryExchange());
    }

}
