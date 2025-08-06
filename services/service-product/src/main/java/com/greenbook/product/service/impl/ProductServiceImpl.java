package com.greenbook.product.service.impl;

import com.greenbook.product.Product;
import com.greenbook.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Product getProductById(Long productId) {
        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal("9.9"));
        product.setProductName("苹果-"+productId);
        product.setNum(100);
        return product;
    }
}
