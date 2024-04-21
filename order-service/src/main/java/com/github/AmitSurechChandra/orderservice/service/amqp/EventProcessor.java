package com.github.AmitSurechChandra.orderservice.service.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.AmitSurechChandra.orderservice.service.OrderService;
import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderPaidEvent;
import com.github.AmitSureshChandra.commonmodule.dto.order.OrderResp;
import com.github.AmitSureshChandra.commonmodule.enums.order.OrderStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EventProcessor {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    public EventProcessor(ObjectMapper objectMapper, OrderService orderService) {
        this.objectMapper = objectMapper;
        this.orderService = orderService;
    }

    public void processPaymentSuccessful(byte[] msg) {
        try {
            System.out.println(new String(msg));
            OrderPaidEvent orderPaidEvent = objectMapper.readValue(msg, OrderPaidEvent.class);
            OrderResp orderResp = orderService.findOrderByRef(orderPaidEvent.getOrderRef());
            orderResp.setStatus(OrderStatus.CONFIRMED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
