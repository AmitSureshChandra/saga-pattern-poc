package com.github.AmitSureshChandra.catalogservice.service.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.AmitSureshChandra.catalogservice.service.CatalogService;
import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderPaidEvent;
import com.github.AmitSureshChandra.commonmodule.dto.order.OrderResp;
import com.github.AmitSureshChandra.commonmodule.enums.catalog.CatalogOperation;
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

    final CatalogService catalogService;

    public EventProcessor(ObjectMapper objectMapper, RestTemplate restTemplate, CatalogService catalogService) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.catalogService = catalogService;
    }

    public void processPaymentSuccessful(byte[] message) {
        try {
            updateCatalog(message, CatalogOperation.SUBTRACT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processOrderCancel(byte[] message) {
        try {
            updateCatalog(message, CatalogOperation.ADD);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCatalog(byte[] message, CatalogOperation opt) throws IOException {
        OrderPaidEvent orderPaidEvent = objectMapper.readValue(message, OrderPaidEvent.class);

        // fetch items with quantity to update stocks
        OrderResp orderResp = restTemplate.exchange(gatewayUrl + "/o/api/v1/orders/internal/" + orderPaidEvent.getOrderRef(), HttpMethod.GET, null, OrderResp.class, new HashMap<>()).getBody();

        orderResp.getItems().forEach((product, quantity) -> {
            catalogService.updateStock(product, opt, quantity);
            System.out.println(product + " stock updated");
        });
    }
}
