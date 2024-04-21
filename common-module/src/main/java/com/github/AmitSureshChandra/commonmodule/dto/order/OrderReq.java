package com.github.AmitSureshChandra.commonmodule.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class OrderReq {
    Map<String, Long> items;
}
