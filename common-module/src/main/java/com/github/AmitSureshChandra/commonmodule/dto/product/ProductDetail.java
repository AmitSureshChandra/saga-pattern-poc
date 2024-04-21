package com.github.AmitSureshChandra.commonmodule.dto.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDetail {
    private String code;
    private String name;
    private BigDecimal price;
    private String description;
    private String imgUrl;

    // other details
}
