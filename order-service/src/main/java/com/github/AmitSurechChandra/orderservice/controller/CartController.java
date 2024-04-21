package com.github.AmitSurechChandra.orderservice.controller;

import com.github.AmitSurechChandra.orderservice.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    Map<String, Long> loadCart(@RequestHeader(name = "token") String token) {
        return cartService.fetchCart(token);
    }

    @PutMapping("/{code}/{quantity}")
    void addItem(@PathVariable(name = "code") String code, @PathVariable(name = "quantity") Long quantity, @RequestHeader(name = "token") String token) {
        cartService.updateCart(code, quantity, token);
    }
}
