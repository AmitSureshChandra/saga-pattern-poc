package com.github.AmitSureshChandra.productservice.controller;

import com.github.AmitSureshChandra.commonmodule.dto.product.ProductDetail;
import com.github.AmitSureshChandra.commonmodule.dto.product.ProductList;
import com.github.AmitSureshChandra.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    List<ProductList> list() {
        return productService.listProducts();
    }

    @PostMapping("/prices")
    Map<String, Double> fetchPrices(@RequestBody Set<String> products) {
        return productService.fetchPrices(products);
    }

    @GetMapping("/{code}")
    ProductDetail fetchProduct(@PathVariable(name = "code") String code) {
        return productService.fetchProduct(code);
    }


}
