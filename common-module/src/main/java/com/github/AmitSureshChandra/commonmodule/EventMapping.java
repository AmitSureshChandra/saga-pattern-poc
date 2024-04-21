package com.github.AmitSureshChandra.commonmodule;

import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderCancelEvent;
import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderPaidEvent;

import java.util.HashMap;

public class EventMapping {
    public static HashMap<Class, String> EVENT_ENUM_MAP = new HashMap<>() {{
        put(OrderPaidEvent.class, "order-paid-event");
        put(OrderCancelEvent.class, "order-cancel-event");
    }};

    public static HashMap<String, Class> EVENT_STRING_MAP = new HashMap<>() {{
        put("order-paid-event",OrderPaidEvent.class);
        put("order-cancel-event", OrderCancelEvent.class);
    }};
}
