package com.github.AmitSureshChandra.integrationtests.integrations;

import com.github.AmitSureshChandra.commonmodule.dto.order.OrderResp;
import com.github.AmitSureshChandra.commonmodule.dto.product.ProductList;
import com.github.AmitSureshChandra.commonmodule.dto.user.LoginResp;
import com.github.AmitSureshChandra.commonmodule.enums.order.OrderStatus;
import com.github.AmitSureshChandra.integrationtests.util.BaseTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserPurchaseProductSuccessTest extends BaseTestContainer {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void user_purchase_product_success() throws InterruptedException {

        Thread.sleep(1000 * 20);

        // list product
        HttpEntity<HashMap> httpEntity = new HttpEntity<>(new HashMap<String, String>(){{
            put("username", "dj2456");
            put("email", "dj@example.com");
            put("mobile", "+91 7237145707");
            put("password", "Pass#123.");
        }}, new LinkedMultiValueMap<>());

        LoginResp loginResp = restTemplate.exchange("http://localhost:8000/users/api/v1/auth/signup", HttpMethod.POST, httpEntity, LoginResp.class, new HashMap<>()).getBody();

        // list products
        List<ProductList> productLists = restTemplate.exchange("http://localhost:8000/pds/api/v1/products", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductList>>() {}, new HashMap<>()).getBody();

        // fetching first product
        String macbookCode = productLists.get(0).getCode();

        Long oldStock = restTemplate.exchange("http://localhost:8000/catalogs/api/v1/products/" + macbookCode, HttpMethod.GET, new HttpEntity<>(
                new String("")
        ), Long.class, new HashMap<>()).getBody();

        // creating order
        OrderResp orderResp = null;

        try {
            orderResp = restTemplate.exchange("http://localhost:8000/o/api/v1/orders", HttpMethod.POST, new HttpEntity<>(
                    new HashMap<String, HashMap<String, String>>() {{
                        put("items", new HashMap<>(){{
                            put(macbookCode, String.valueOf(1));
                        }});
                    }},
                    new LinkedMultiValueMap<>(){{
                        put("token", Collections.singletonList(loginResp.getToken()));
                    }}
            ), OrderResp.class, new HashMap<>()).getBody();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println(exception.getMessage());
        }

        // adding 2L money in wallet
        Double newBal = restTemplate.exchange("http://localhost:8000/pay/api/v1/wallet/200000", HttpMethod.PUT, new HttpEntity<>(
                new String(""),
                new LinkedMultiValueMap<>(){{
                    put("token", Collections.singletonList(loginResp.getToken()));
                }}
        ), Double.class, new HashMap<>()).getBody();

        // order payment
        restTemplate.exchange("http://localhost:8000/pay/api/v1/orders/" + orderResp.getOrderRef(), HttpMethod.POST, new HttpEntity<>(
                new String(""),
                new LinkedMultiValueMap<>(){{
                    put("token", Collections.singletonList(loginResp.getToken()));
                }}
        ), Void.class, new HashMap<>()).getBody();

        Thread.sleep(1000 * 5);

        // check order status
        OrderResp newOrderResp = restTemplate.exchange("http://localhost:8000/o/api/v1/orders/" + orderResp.getOrderRef(), HttpMethod.GET, new HttpEntity<>(
                new String(""),
                new LinkedMultiValueMap<>(){{
                    put("token", Collections.singletonList(loginResp.getToken()));
                }}
        ), OrderResp.class, new HashMap<>()).getBody();

        assertEquals(OrderStatus.CONFIRMED, newOrderResp.getStatus());

        // check new stock
        Long newStock = restTemplate.exchange("http://localhost:8000/catalogs/api/v1/products/" + macbookCode, HttpMethod.GET, new HttpEntity<>(
                new String("")
        ), Long.class, new HashMap<>()).getBody();

        assertEquals(oldStock - 1, newStock);

        // fetch updated balance
        Double updatedBal = restTemplate.exchange("http://localhost:8000/pay/api/v1/wallet", HttpMethod.GET, new HttpEntity<>(
                new String(""),
                new LinkedMultiValueMap<>(){{
                    put("token", Collections.singletonList(loginResp.getToken()));
                }}
        ), Double.class, new HashMap<>()).getBody();

        assertEquals(newBal.doubleValue(), updatedBal + orderResp.getNetTotal().doubleValue());
    }
}
