package com.github.AmitSurechChandra.orderservice.service.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    private final EventProcessor eventProcessor;

    public EventListener(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    @RabbitListener(queues = {"${spring.application.name}"})
    void updateOrderStatus(Message message) throws ClassNotFoundException {
        String event = message.getMessageProperties().getHeader("event");
        System.out.println(new String(message.getBody()));
        switch (event) {
            case "order-paid-event":
                eventProcessor.processPaymentSuccessful(message.getBody());
                break;
            default:
                System.out.println("unknown event : " + event);
        }
    }
}
