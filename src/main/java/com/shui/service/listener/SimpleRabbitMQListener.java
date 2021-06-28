package com.shui.service.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author shui
 */
@Component
@Slf4j
public class SimpleRabbitMQListener {

    @RabbitListener(queues = "queue_simple")
    public void simpleListenter(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws Exception {
        if (!Objects.isNull(message.getBody())) {
            log.info("message:" + message.getBody());
            channel.basicAck(tag, false);
        }
    }

}
