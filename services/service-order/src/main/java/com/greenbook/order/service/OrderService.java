package com.greenbook.order.service;

import com.greenbook.order.Order;

public interface OrderService {
    Order createOrder(Long userId, Long productId);
}
