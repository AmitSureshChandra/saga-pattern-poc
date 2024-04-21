package com.github.AmitSurechChandra.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {
    private String code;
    private double price;
}
