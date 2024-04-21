package com.github.AmitSureshChandra.productservice.service;

import com.github.AmitSureshChandra.commonmodule.dto.product.ProductDetail;
import com.github.AmitSureshChandra.commonmodule.dto.product.ProductList;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    Map<String, ProductDetail> products = new HashMap<>();

    private final ModelMapper modelMapper;

    public ProductService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    void init() {
        products.put(
                "macbook_m1_air",
                ProductDetail.builder()
                        .code("macbook_m1_air")
                        .name("macbook air m1")
                        .description("8/256 model")
                        .price(BigDecimal.valueOf(80000))
                        .imgUrl("https://rukminim2.flixcart.com/image/832/832/kp5sya80/screen-guard/tempered-glass/o/v/n/apple-macbook-air-m1-13-3-inch-lightwings-original-imag3gh5xftgbpg3.jpeg")
                        .build()
        );
    }

    public List<ProductList> listProducts() {
        return products.values().stream()
                .map(p -> modelMapper.map(p, ProductList.class))
                .collect(Collectors.toList());
    }

    public ProductDetail fetchProduct(String code) {
        return products.get(code);
    }

    public Map<String, Double> fetchPrices(Set<String> productCodes) {
        return products.keySet().stream().filter(productCodes::contains).collect(Collectors.toMap(product -> products.get(product).getCode(), product -> products.get(product).getPrice().doubleValue()));
    }
}
