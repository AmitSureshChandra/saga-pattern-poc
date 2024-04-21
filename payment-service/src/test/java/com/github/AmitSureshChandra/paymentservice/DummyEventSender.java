package com.github.AmitSureshChandra.paymentservice;

import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderPaidEvent;
import com.github.AmitSureshChandra.paymentservice.service.AmqpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static com.github.AmitSureshChandra.commonmodule.EventMapping.EVENT_ENUM_MAP;

@SpringBootTest
public class DummyEventSender {
    @Autowired
    AmqpService amqpService;

    @Test
    void sendEvent() {
        amqpService.send(EVENT_ENUM_MAP.get(OrderPaidEvent.class), new OrderPaidEvent(UUID.fromString("5ff0eab1-dc83-4a43-abf5-a3dd95afbca3").toString(), "akumar00029"));
    }
}
