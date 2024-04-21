package com.AmitSureshChandra.shippingservice.service.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderPaidEvent;
import com.github.AmitSureshChandra.commonmodule.dto.order.OrderResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;

@Service
public class EventProcessor {

    @Value("${gateway-url}")
    String gatewayUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final ShippingService shippingService;

    public EventProcessor(RestTemplate restTemplate, ObjectMapper objectMapper, ShippingService shippingService) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.shippingService = shippingService;
    }

    public void startShipping(byte[] msg) {
        try {
            OrderPaidEvent orderPaidEvent = objectMapper.readValue(msg, OrderPaidEvent.class);

            // fetch items with quantity to update stocks
            OrderResp orderResp = restTemplate.exchange(gatewayUrl + "/o/api/v1/internal/orders/" + orderPaidEvent.getOrderRef(), HttpMethod.GET, null, OrderResp.class, new HashMap<>()).getBody();

            shippingService.startShipping(orderResp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelShipping(byte[] msg) {
        try {
            OrderPaidEvent orderPaidEvent = objectMapper.readValue(msg, OrderPaidEvent.class);

            // fetch items with quantity to update stocks
            OrderResp orderResp = restTemplate.exchange(gatewayUrl + "/o/api/v1/internal/orders/" + orderPaidEvent.getOrderRef(), HttpMethod.GET, null, OrderResp.class, new HashMap<>()).getBody();

            shippingService.cancelShipping(orderResp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
