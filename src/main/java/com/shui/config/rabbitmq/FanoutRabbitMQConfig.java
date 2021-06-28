package com.shui.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shui
 */
@Configuration
public class FanoutRabbitMQConfig {

    @Bean
    public Queue fanoutQueueA() {
        return new Queue("fanout.A", true);
    }

    @Bean
    public Queue fanoutQueueB() {
        return new Queue("fanout.B", true);
    }

    @Bean
    public Queue fanoutQueueC() {
        return new Queue("fanout.C", true);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange", true, false);
    }

    @Bean
    Binding bindingFanoutExchangeA() {
        return BindingBuilder.bind(fanoutQueueA()).to(fanoutExchange());
    }

    @Bean
    Binding bindingFanoutExchangeB() {
        return BindingBuilder.bind(fanoutQueueB()).to(fanoutExchange());
    }

    @Bean
    Binding bindingFanoutExchangeC() {
        return BindingBuilder.bind(fanoutQueueC()).to(fanoutExchange());
    }
}
