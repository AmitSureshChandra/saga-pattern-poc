package com.github.AmitSurechChandra.orderservice.service;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    Map<String, Map<String, Long>> carts = new HashMap<>();

    private final StockCheckService stockCheckService;

    private final TokenService tokenService;

    public CartService(StockCheckService stockCheckService, TokenService tokenService) {
        this.stockCheckService = stockCheckService;
        this.tokenService = tokenService;
    }

    public Map<String, Long> fetchCart(String token) {
        String user = tokenService.fetchUsername(token);
        return carts.get(user);
    }

    public void updateCart(String code, Long quantity, String token) {
        // check stock
        if(!stockCheckService.inStock(code, quantity)) throw new ValidationException("product out of stock");

        String user = tokenService.fetchUsername(token);
        carts.putIfAbsent(user, new HashMap<>());
        carts.get(user).put(code, quantity);
    }
}
