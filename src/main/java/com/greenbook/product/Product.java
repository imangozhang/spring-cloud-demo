package com.greenbook.product;

import java.math.BigDecimal;

public record Product(long id, String sku, String productName, BigDecimal price) {
}
