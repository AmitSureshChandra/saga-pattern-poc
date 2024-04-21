package com.AmitSureshChandra.shippingservice.service.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventListener {
    private final ShippingService shippingService;

    private final EventProcessor eventProcessor;

    public EventListener(ShippingService shippingService, EventProcessor eventProcessor) {
        this.shippingService = shippingService;
        this.eventProcessor = eventProcessor;
    }

    @RabbitListener(queues = {"${spring.application.name}"})
    void orderPayListener(Message message) throws ClassNotFoundException {
        String event = message.getMessageProperties().getHeader("event");
        System.out.println(new String(message.getBody()));
        switch (event) {
            case "order-paid-event":
                eventProcessor.startShipping(message.getBody());
                break;
            case "order-cancel-event":
                eventProcessor.cancelShipping(message.getBody());
                break;
            default:
                System.out.println("unknown event : " + event);
        }
    }

}
