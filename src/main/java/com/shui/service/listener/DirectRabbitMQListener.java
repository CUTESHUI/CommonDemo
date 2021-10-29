package com.shui.service.listener;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author shui
 */
@Component
@Slf4j
public class DirectRabbitMQListener {

    @RabbitListener(queues = "direct.A", concurrency = "4")
    public void fanoutAListenter(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag, @Header(AmqpHeaders.MESSAGE_ID) Object messageId, @Header(AmqpHeaders.CORRELATION_ID) Object correlationId) throws Exception {
        MessageHeaders messageHeaders = message.getHeaders();
        String id = (String) messageHeaders.get("spring_returned_message_correlation");
        JSONObject payLoad = JSONObject.parseObject(JSONObject.toJSONString(message.getPayload()));

        log.warn("message:{}, {}", id, payLoad);
        log.warn("messageId:{}", messageId);
        log.warn("correlationId:{}", correlationId);
        channel.basicAck(tag, false);
    }


//    @RabbitListener(queues = "direct.A")
//    public void fanoutAListenter1(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag, @Header(AmqpHeaders.MESSAGE_ID) Object messageId, @Header(AmqpHeaders.CORRELATION_ID) Object correlationId) throws Exception {
//        MessageHeaders messageHeaders = message.getHeaders();
//        String id = (String) messageHeaders.get("spring_returned_message_correlation");
//        JSONObject payLoad = JSONObject.parseObject(JSONObject.toJSONString(message.getPayload()));
//
//        log.warn("message1:{}, {}", id, payLoad);
//        log.warn("messageId:{}", messageId);
//        log.warn("correlationId:{}", correlationId);
//        channel.basicAck(tag, false);
//    }
//
//    @RabbitListener(queues = "direct.A")
//    public void fanoutAListenter2(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag, @Header(AmqpHeaders.MESSAGE_ID) Object messageId, @Header(AmqpHeaders.CORRELATION_ID) Object correlationId) throws Exception {
//        MessageHeaders messageHeaders = message.getHeaders();
//        String id = (String) messageHeaders.get("spring_returned_message_correlation");
//        JSONObject payLoad = JSONObject.parseObject(JSONObject.toJSONString(message.getPayload()));
//
//        log.warn("message2:{}, {}", id, payLoad);
//        log.warn("messageId:{}", messageId);
//        log.warn("correlationId:{}", correlationId);
//        channel.basicAck(tag, false);
//    }
//
//    @RabbitListener(queues = "direct.A")
//    public void fanoutAListenter3(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag, @Header(AmqpHeaders.MESSAGE_ID) Object messageId, @Header(AmqpHeaders.CORRELATION_ID) Object correlationId) throws Exception {
//        MessageHeaders messageHeaders = message.getHeaders();
//        String id = (String) messageHeaders.get("spring_returned_message_correlation");
//        JSONObject payLoad = JSONObject.parseObject(JSONObject.toJSONString(message.getPayload()));
//
//        log.warn("message3:{}, {}", id, payLoad);
//        log.warn("messageId:{}", messageId);
//        log.warn("correlationId:{}", correlationId);
//        channel.basicAck(tag, false);
//    }
//
//    @RabbitListener(queues = "direct.A")
//    public void fanoutAListenter4(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag, @Header(AmqpHeaders.MESSAGE_ID) Object messageId, @Header(AmqpHeaders.CORRELATION_ID) Object correlationId) throws Exception {
//        MessageHeaders messageHeaders = message.getHeaders();
//        String id = (String) messageHeaders.get("spring_returned_message_correlation");
//        JSONObject payLoad = JSONObject.parseObject(JSONObject.toJSONString(message.getPayload()));
//
//        log.warn("message4:{}, {}", id, payLoad);
//        log.warn("messageId:{}", messageId);
//        log.warn("correlationId:{}", correlationId);
//        channel.basicAck(tag, false);
//    }
}
