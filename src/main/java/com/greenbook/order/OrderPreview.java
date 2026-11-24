package com.greenbook.order;

import java.math.BigDecimal;

public record OrderPreview(long productId, int quantity, BigDecimal unitPrice, BigDecimal totalAmount) {
}
