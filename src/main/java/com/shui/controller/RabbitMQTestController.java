package com.shui.controller;

import com.shui.common.Result;
import com.shui.utils.SpringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author shui
 */
@Api(tags = "rabbitmq测试")
@RestController
@RequestMapping("/mq")
public class RabbitMQTestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Resource(name = "normalRabbitTemplate")
    private RabbitTemplate normalRabbitTemplate;

    @PostMapping("test")
    public void test() {
        rabbitTemplate.convertAndSend("queue_simple", "你好");
    }

    @PostMapping("test2")
    public void test2() {
        // 手动，区别于注入的，可定制化
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUsername("dev");
        cachingConnectionFactory.setPassword("dev");
        cachingConnectionFactory.setHost("localhost");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setVirtualHost("/");
        RabbitTemplate mq = new RabbitTemplate(cachingConnectionFactory);
        mq.convertAndSend("queue_simple", "你好");
    }

    @PostMapping("test3")
    public void test3() {
        RabbitTemplate normalRabbitTemplate = SpringUtils.getBean("normalRabbitTemplate");
        normalRabbitTemplate.convertAndSend("queue_simple", "你好");
    }

    @PostMapping("test4")
    public void test4() {
        normalRabbitTemplate.convertAndSend("queue_simple", "你好");
    }

    @GetMapping("/sendFanoutMessage")
    @ApiOperation("fanout")
    public Result sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        Map<String, Object> map = new HashMap<>(8);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", new Date());
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(messageId);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map, correlationData);
        return new Result();
    }

    @GetMapping("/sendDirectMessage")
    @ApiOperation("direct")
    public Result sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testDirectMessage ";
        Map<String, Object> map = new HashMap<>(8);
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", new Date());
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(messageId);
        rabbitTemplate.convertAndSend("directExchange", "direct-A", map, correlationData);
        return new Result();
    }
}
