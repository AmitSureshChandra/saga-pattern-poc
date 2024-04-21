package com.github.AmitSureshChandra.catalogservice.controller;

import com.github.AmitSureshChandra.catalogservice.service.CatalogService;
import com.github.AmitSureshChandra.commonmodule.enums.catalog.CatalogOperation;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products")
@RestController
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/{code}")
    Long getStock(@PathVariable(name = "code") String code) {
        return catalogService.getStock(code);
    }

    @PutMapping("/{code}/{operation}/{quantity}")
    void updateStock(
            @PathVariable(name = "code") String code,
            @PathVariable(name = "operation") CatalogOperation operation,
            @PathVariable(name = "quantity") long quantity
    ) {
        catalogService.updateStock(code, operation, quantity);
    }
}
