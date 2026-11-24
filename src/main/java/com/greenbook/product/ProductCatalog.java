package com.greenbook.product;

import java.util.Optional;

public interface ProductCatalog {

    Optional<Product> findById(long id);

    Optional<Product> findBySku(String sku);
}
