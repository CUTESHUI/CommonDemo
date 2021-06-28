package com.shui.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shui
 */
@Configuration
public class SimpleRabbitMQConfig {

    @Bean
    public Queue queueSimple() {
        return QueueBuilder.durable("queue_simple").build();
    }
}
