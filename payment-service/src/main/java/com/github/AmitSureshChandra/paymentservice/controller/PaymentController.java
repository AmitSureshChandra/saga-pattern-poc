package com.github.AmitSureshChandra.paymentservice.controller;

import com.github.AmitSureshChandra.paymentservice.service.WalletService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class PaymentController {
    private final WalletService walletService;

    public PaymentController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/{order}")
    void payment(@PathVariable String order, @RequestHeader(name = "token") String token) {
        walletService.pay(token, order);
    }
}
