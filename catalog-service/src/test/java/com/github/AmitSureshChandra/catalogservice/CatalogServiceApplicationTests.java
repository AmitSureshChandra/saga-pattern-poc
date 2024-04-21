package com.github.AmitSureshChandra.catalogservice;

import com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderPaidEvent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CatalogServiceApplicationTests {

	@Test
	void contextLoads() throws ClassNotFoundException {
		Class<?> clazz = Class.forName("com.github.AmitSureshChandra.commonmodule.dto.amqp.OrderPaidEvent");
		System.out.println(clazz.getName());
		System.out.println(OrderPaidEvent.class.getName());
	}
}
