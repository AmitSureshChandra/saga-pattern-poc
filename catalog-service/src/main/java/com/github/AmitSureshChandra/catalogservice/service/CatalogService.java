package com.github.AmitSureshChandra.catalogservice.service;

import com.github.AmitSureshChandra.commonmodule.enums.catalog.CatalogOperation;
import com.github.AmitSureshChandra.commonmodule.exception.OutofStockException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CatalogService {
    Map<String, Long> catalog = new HashMap<>();

    @PostConstruct
    void init() {
        catalog.put("macbook_m1_air", 10L);
    }

    public Long getStock(String code) {
        return catalog.get(code);
    }

    public void updateStock(String code, CatalogOperation operation, long quantity) throws OutofStockException {

        // if code not exists
        if(!catalog.containsKey(code)) return;
        if(operation.equals(CatalogOperation.ADD)) {
            catalog.put(code, catalog.get(code) + quantity);
            return;
        }

        // if incr stock
        if(catalog.get(code) < quantity) {
            throw new OutofStockException(code);
        }

        // if decr stock
        catalog.put(code, catalog.get(code) - quantity);
    }
}
