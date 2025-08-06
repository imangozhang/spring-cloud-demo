package com.greenbook.product.controller;

import com.greenbook.product.Product;
import com.greenbook.product.service.ProductService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
//@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        log.info("get product by id: {}", id);
        Product product = productService.getProductById(id);
        return product;
    }
}
