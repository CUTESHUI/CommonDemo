package com.shui.config.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 如果消息没到exchange, 则confirm回调, ack=false
        // 如果消息到达exchange, 则confirm回调, ack=true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
            } else {
                log.info("消息发送失败:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
            }
        });

        // 如果exchange到queue成功, 则不回调return
        // 如果exchange到queue失败, 则回调return
        // 需设置mandatory=true, 否则不回回调, 消息就丢了
        rabbitTemplate.setReturnsCallback(returned ->
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",
                        returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), returned.getMessage()
                )
        );

        return rabbitTemplate;
    }

}
