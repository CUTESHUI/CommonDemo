package com.shui.service.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author shui
 */
@Component
@Slf4j
public class DirectRabbitMQListener {

    @RabbitListener(queues = "direct.A")
    public void fanoutAListenter(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws Exception {
        if (!Objects.isNull(message.getBody())) {
            log.info("message:{}", new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(tag, false);
        }
    }
}
