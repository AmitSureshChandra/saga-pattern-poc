package com.github.AmitSureshChandra.commonmodule.dto.amqp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaidEvent {
    private String orderRef;
    private String username;
}
