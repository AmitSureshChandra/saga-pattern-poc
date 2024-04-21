package com.github.AmitSureshChandra.eventlogservice.service.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventLogListener {

    Map<String, List<String>> eventLog = new HashMap<>();

    @RabbitListener(queues = {"${spring.application.name}"})
    void listen(Message message) {
        String event = message.getMessageProperties().getHeader("event");
        eventLog.putIfAbsent(event, new ArrayList<>());
        eventLog.get(event).add(new String(message.getBody()));
        System.out.println("!!!!! New msg !!!!!");
        System.out.println("event : " + event);
        System.out.println(new String(message.getBody()));
    }
}
