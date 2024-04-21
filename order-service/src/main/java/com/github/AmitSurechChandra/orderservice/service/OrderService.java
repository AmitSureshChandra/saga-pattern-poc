package com.github.AmitSurechChandra.orderservice.service;

import com.github.AmitSureshChandra.commonmodule.EventMapping;
import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderCancelEvent;
import com.github.AmitSureshChandra.commonmodule.dto.order.OrderReq;
import com.github.AmitSureshChandra.commonmodule.dto.order.OrderResp;
import com.github.AmitSureshChandra.commonmodule.enums.order.OrderStatus;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;


@Service
public class OrderService {

    @Value("${gateway-url}")
    String gatewayUrl;

    private final AmqpService amqpService;
    private final RestTemplate restTemplate;
    private final StockCheckService stockCheckService;
    private final TokenService tokenService;

    Map<String, Map<String, OrderResp>> orders = new HashMap<>();

    public OrderService(AmqpService amqpService, RestTemplate restTemplate, StockCheckService stockCheckService, TokenService tokenService) {
        this.amqpService = amqpService;
        this.restTemplate = restTemplate;
        this.stockCheckService = stockCheckService;
        this.tokenService = tokenService;
    }

    public OrderResp createOrder(OrderReq orderReq, String token) {

        // check stock
        orderReq.getItems().forEach((product, quantity) -> {
            if (!stockCheckService.inStock(product, quantity)) throw new ValidationException(product + " is out of stock for quantity " + quantity);
        });

        String user = tokenService.fetchUsername(token);

        var orderResp = new OrderResp();

        orderResp.setItems(orderReq.getItems());
        orderResp.setStatus(OrderStatus.CREATED);
        orderResp.setOrderRef(UUID.randomUUID().toString());

        // calculate items price
        double totalItemPrice = calOrderTotal(orderReq);

        orderResp.setShipping(totalItemPrice < 500 ? BigDecimal.valueOf(40) : BigDecimal.ZERO);
        orderResp.setTax(BigDecimal.ZERO);

        orderResp.setGrossTotal(
                BigDecimal.valueOf(totalItemPrice)
                        .add(orderResp.getShipping())
        );

        orderResp.setNetTotal(
                orderResp.getGrossTotal()
                        .add(orderResp.getTax())
        );

        orders.putIfAbsent(user, new HashMap<>());
        orders.get(user).put(orderResp.getOrderRef(), orderResp);
        return orderResp;
    }

    private double calOrderTotal(OrderReq orderReq) {
        HttpEntity httpEntity = new HttpEntity<Set>(new HashSet<>(orderReq.getItems().keySet()));
        HashMap<String, Double> priceMap = (HashMap<String, Double>)restTemplate.exchange(gatewayUrl + "/pds/api/v1/products/prices", HttpMethod.POST, httpEntity, Map.class, new HashMap<>()).getBody();

        return orderReq.getItems().keySet().stream()
                .mapToDouble(product -> priceMap.get(product) * orderReq.getItems().get(product))
                .sum();
    }

    public OrderResp fetchOrder(String orderRef, String token) {
        String user = tokenService.fetchUsername(token);
        return orders.get(user).get(orderRef);
    }

    public Map<String, OrderResp> fetchAllOrders(String token) {
        String user = tokenService.fetchUsername(token);
        return orders.get(user);
    }

    public OrderResp findOrderByRef(String orderRef) {
        Optional<Map<String, OrderResp>> orderRespOpt = orders.values().stream().filter(map -> map.containsKey(orderRef)).findFirst();
        return orderRespOpt.map(stringOrderRespMap -> stringOrderRespMap.get(orderRef)).orElse(null);
    }

    public OrderResp cancelOrder(String orderRef, String token) {

        String user = tokenService.fetchUsername(token);

        OrderResp orderResp = fetchOrder(orderRef, token);
        orderResp.setStatus(OrderStatus.CANCELLED);

        // raise events for compensation to catalog, shipping & payment-service

        amqpService.send(EventMapping.EVENT_ENUM_MAP.get(OrderCancelEvent.class), new OrderCancelEvent(orderRef, user));
        return orderResp;
    }
}
