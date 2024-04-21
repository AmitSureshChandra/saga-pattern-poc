package com.github.AmitSureshChandra.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderPaidEvent;
import com.github.AmitSureshChandra.commonmodule.dto.order.OrderResp;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

import static com.github.AmitSureshChandra.commonmodule.EventMapping.EVENT_ENUM_MAP;

@Service
public class WalletService {
    @Value("${gateway-url}")
    String gatewayUrl;

    private final TokenService tokenService;
    private final AmqpService amqpService;
    private final RestTemplate restTemplate;

    Map<String, BigDecimal> wallet = new HashMap<>();


    public WalletService(TokenService tokenService, AmqpService amqpService, RestTemplate restTemplate) {
        this.tokenService = tokenService;
        this.amqpService = amqpService;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    void init() throws JsonProcessingException {
        wallet.put("dj2456", BigDecimal.valueOf(30000));
        wallet.put("ak00029", BigDecimal.valueOf(300000));
    }

    public void pay(String token, String order) {
        String user = tokenService.fetchUsername(token);
        BigDecimal walletAmt = wallet.get(user);
        LinkedMultiValueMap<String, String > headers = new LinkedMultiValueMap<>();
        headers.put("token", Collections.singletonList(token));
        OrderResp orderResp = restTemplate.exchange(gatewayUrl + "/o/api/v1/orders/" + order, HttpMethod.GET, new HttpEntity<>(headers), OrderResp.class, new HashMap<>()).getBody();

        if(walletAmt == null || walletAmt.compareTo(orderResp.getNetTotal()) < 0) {
            throw new ValidationException("not enough funds");
        }

        // decrease fund
        wallet.put(user, wallet.get(user).subtract(orderResp.getNetTotal()));

        // send event of payment_received

        // topic ecommerce-exchange ->  route_key (payment_done.*) -> payment_done.update_inventory_event, payment_done.shipping_event, payment_done.update_order_event
        amqpService.send(EVENT_ENUM_MAP.get(OrderPaidEvent.class), new OrderPaidEvent(order, user));
        System.out.println("payment event sent");
    }

    public Double fetchBalance(String token) {
        String user = tokenService.fetchUsername(token);
        return wallet.get(user).doubleValue();
    }

    public Double addBalance(String token, BigDecimal amt) {
        String user = tokenService.fetchUsername(token);
        return addBalanceByUsername(user, amt);
    }

    public Double addBalanceByUsername(String username, BigDecimal amt) {
        wallet.put(username, wallet.get(username).add(amt));
        return wallet.get(username).doubleValue();
    }

    public void refund(String username, BigDecimal netTotal) {
        addBalanceByUsername(username, netTotal);
    }
}
