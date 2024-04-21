package com.AmitSureshChandra.shippingservice.service.amqp;

import com.github.AmitSureshChandra.commonmodule.dto.order.OrderResp;
import com.github.AmitSureshChandra.commonmodule.dto.shipping.ShippingDto;
import com.github.AmitSureshChandra.commonmodule.enums.shipping.ShippingStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShippingService {
    Map<String, ShippingDto> shippings = new HashMap<>();

    public void startShipping(OrderResp orderResp) {
        ShippingDto shippingDto = new ShippingDto();
        shippingDto.setOrderRef(orderResp.getOrderRef());
        shippingDto.setStatus(ShippingStatus.IN_TRANSIT);
        shippings.put(orderResp.getOrderRef(), shippingDto);
    }

    public void cancelShipping(OrderResp orderResp) {
        ShippingDto shippingDto = shippings.get(orderResp.getOrderRef());
        shippingDto.setStatus(ShippingStatus.CANCELLED);
    }
}
