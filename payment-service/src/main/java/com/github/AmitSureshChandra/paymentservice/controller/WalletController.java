package com.github.AmitSureshChandra.paymentservice.controller;

import com.github.AmitSureshChandra.paymentservice.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    Double getBalance(@RequestHeader(name = "token") String token) {
        return walletService.fetchBalance(token);
    }

    @PutMapping("/{amt}")
    Double addBalance(@RequestHeader(name = "token") String token, @PathVariable BigDecimal amt) {
        return walletService.addBalance(token, amt);
    }
}
