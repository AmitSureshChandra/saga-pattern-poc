package com.github.AmitSureshChandra.commonmodule.dto.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDto {
    String productCode;
    Long quantity;
}
