package com.greenbook.product;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryProductCatalog implements ProductCatalog {

    private final Product sample = new Product(1L, "s10000678", "红牛", new BigDecimal("4.50"));

    @Override
    public Optional<Product> findById(long id) {
        return id == sample.id() ? Optional.of(sample) : Optional.empty();
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return sample.sku().equals(sku) ? Optional.of(sample) : Optional.empty();
    }
}
