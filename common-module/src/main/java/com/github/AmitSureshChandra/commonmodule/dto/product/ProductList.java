package com.github.AmitSureshChandra.commonmodule.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductList {
    private String code;
    private String name;
    private BigDecimal price;
    private String description;
    private String imgUrl;
}
