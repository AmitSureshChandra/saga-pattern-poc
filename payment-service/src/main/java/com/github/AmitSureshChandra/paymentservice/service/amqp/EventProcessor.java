package com.github.AmitSureshChandra.paymentservice.service.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderPaidEvent;
import com.github.AmitSureshChandra.commonmodule.dto.order.OrderResp;
import com.github.AmitSureshChandra.paymentservice.service.WalletService;
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

    final ObjectMapper objectMapper;
    final RestTemplate restTemplate;

    final WalletService walletService;

    public EventProcessor(ObjectMapper objectMapper, RestTemplate restTemplate, WalletService walletService) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.walletService = walletService;
    }

    public void refundMoney(byte[] message) throws IOException {
        OrderPaidEvent orderPaidEvent = objectMapper.readValue(message, OrderPaidEvent.class);

        // fetch items with quantity to update stocks
        OrderResp orderResp = restTemplate.exchange(gatewayUrl + "/o/api/v1/orders/internal/" + orderPaidEvent.getOrderRef(), HttpMethod.GET, null, OrderResp.class, new HashMap<>()).getBody();

        orderResp.getItems().forEach((product, quantity) -> {
        });
        walletService.refund(orderPaidEvent.getUsername(), orderResp.getNetTotal());
        System.out.println(orderResp.getNetTotal() + " refunded");
    }
}
