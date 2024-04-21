package com.github.AmitSureshChandra.gatewayservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Value("${user-service-url}")
    String userServiceUrl;
    @Value("${product-service-url}")
    String productServiceUrl;
    @Value("${catalog-service-url}")
    String catalogServiceUrl;
    @Value("${order-service-url}")
    String orderServiceUrl;
    @Value("${payment-service-url}")
    String paymentServiceUrl;

    @Value("${shipping-service-url}")
    String shippingServiceUrl;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/users/**").uri(userServiceUrl))
                .route("product-service", r -> r.path("/pds/**").uri(productServiceUrl))
                .route("catalog-service", r -> r.path("/catalogs/**").uri(catalogServiceUrl))
                .route("order-service", r -> r.path("/o/**").uri(orderServiceUrl))
                .route("payment-service", r -> r.path("/pay/**").uri(paymentServiceUrl))
                .route("shipping-service", r -> r.path("/shipping/**").uri(shippingServiceUrl))
                .build();
    }
}
