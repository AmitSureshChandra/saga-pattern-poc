package com.github.AmitSureshChandra.commonmodule.dto.order;

import com.github.AmitSureshChandra.commonmodule.enums.order.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
public class OrderResp {
    private String orderRef;
    private BigDecimal netTotal;
    private BigDecimal grossTotal;
    private BigDecimal shipping;
    private BigDecimal tax;
    private OrderStatus status;
    private Map<String, Long> items;
}
