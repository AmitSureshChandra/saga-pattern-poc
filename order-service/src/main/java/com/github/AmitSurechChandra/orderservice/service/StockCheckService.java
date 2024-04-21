package com.github.AmitSurechChandra.orderservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class StockCheckService {
    @Value("${gateway-url}")
    String gatewayUrl;

    private final RestTemplate restTemplate;

    public StockCheckService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean inStock(String code, Long quantity) {
        Long inStock = fetchStock(code);
        return inStock >= quantity;
    }

    public Long fetchStock(String productCode) {
        return restTemplate.exchange(gatewayUrl + "/catalogs/api/v1/products/" + productCode, HttpMethod.GET, null, Long.class, new HashMap<>()).getBody();
    }
}
