package com.github.AmitSureshChandra.catalogservice.service.amqp;

import com.github.AmitSureshChandra.commonmodule.enums.catalog.CatalogOperation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EventListener {
    final String appName;
    final EventProcessor eventProcessor;

    public EventListener(@Value("${spring.application.name}") String appName, EventProcessor eventProcessor) {
        this.appName = appName;
        this.eventProcessor = eventProcessor;
    }

    @RabbitListener(queues = {"${spring.application.name}"})
    public void listenEvents(Message message) throws ClassNotFoundException, IOException {
        String event = message.getMessageProperties().getHeader("event");
        System.out.println(new String(message.getBody()));
        switch (event) {
            case "order-paid-event":
                eventProcessor.updateCatalog(message.getBody(), CatalogOperation.SUBTRACT);
                break;
            case "order-cancel-event":
                eventProcessor.updateCatalog(message.getBody(), CatalogOperation.ADD);
                break;
            default:
                System.out.println("unknown event : " + event);
        }
    }
}
