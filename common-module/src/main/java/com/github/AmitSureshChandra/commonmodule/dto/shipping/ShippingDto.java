package com.github.AmitSureshChandra.commonmodule.dto.shipping;

import com.github.AmitSureshChandra.commonmodule.enums.shipping.ShippingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingDto {
    private String orderRef;
    private ShippingStatus status;

    // need to add address
}
