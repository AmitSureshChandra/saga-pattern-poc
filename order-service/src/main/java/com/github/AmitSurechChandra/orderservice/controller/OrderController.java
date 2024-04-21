package com.github.AmitSurechChandra.orderservice.controller;

import com.github.AmitSurechChandra.orderservice.service.OrderService;
import com.github.AmitSureshChandra.commonmodule.dto.order.OrderReq;
import com.github.AmitSureshChandra.commonmodule.dto.order.OrderResp;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/{orderRef}")
    OrderResp fetchOrder(@PathVariable String orderRef, @RequestHeader(name = "token") String token) {
        return orderService.fetchOrder(orderRef, token);
    }

    @PutMapping("/{orderRef}/cancel")
    void cancelOrder(@PathVariable String orderRef, @RequestHeader(name = "token") String token) {
        orderService.cancelOrder(orderRef, token);
    }

    @GetMapping("/internal/{orderRef}")
    OrderResp fetchOrder(@PathVariable String orderRef) {
        return orderService.findOrderByRef(orderRef);
    }

    @GetMapping
    Map<String, OrderResp> fetchAllOrders(@RequestHeader(name = "token") String token) {
        return orderService.fetchAllOrders(token);
    }

    @PostMapping
    OrderResp createOrder(@RequestBody OrderReq orderReq, @RequestHeader(name = "token") String token) {
        return orderService.createOrder(orderReq, token);
    }
}
