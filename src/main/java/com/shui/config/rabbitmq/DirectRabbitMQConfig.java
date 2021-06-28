package com.shui.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shui
 */
@Configuration
public class DirectRabbitMQConfig {

    @Bean
    public Queue directQueueA() {
        return new Queue("direct.A", true);
    }

    @Bean
    public Queue directQueueB() {
        return new Queue("direct.B", true);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange", true, false);
    }

    @Bean
    Binding bindingDirectExchangeA() {
        return BindingBuilder.bind(directQueueA()).to(directExchange()).with("direct-A");
    }

    @Bean
    Binding bindingDirectExchangeB() {
        return BindingBuilder.bind(directQueueB()).to(directExchange()).with("direct-B");
    }

}
