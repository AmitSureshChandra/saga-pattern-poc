package com.github.AmitSurechChandra.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;

@Service
public class AmqpService {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public AmqpService(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(String event, byte[] msg) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("event", event);
        Message message = new Message(msg, messageProperties);
        amqpTemplate.convertAndSend("ecommerce-exchange", "order-service.order." + event, message);
    }

    public <T> void send(String event, T msg) {
        try {
            send(event, objectMapper.writeValueAsBytes(msg));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
