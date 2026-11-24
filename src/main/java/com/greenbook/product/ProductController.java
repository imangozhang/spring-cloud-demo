package com.greenbook.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductCatalog catalog;

    public ProductController(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable long id) {
        return catalog.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "商品不存在"));
    }

    @GetMapping("/by-sku/{sku}")
    public Product findBySku(@PathVariable String sku) {
        return catalog.findBySku(sku).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "商品不存在"));
    }
}
