package com.greenbook.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.greenbook.order.Order;
import com.greenbook.order.properties.OrderProperties;
import com.greenbook.order.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@Slf4j
@RestController
//@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderProperties orderProperties;

    @GetMapping("/config")
    public String getConfig() {
        return "orderTimeout=" + orderProperties.getTimeout() + ", orderAutoConfirm=" + orderProperties.getAutoConfirm() + ", dbUrl=" + orderProperties.getDbUrl();
    }

    @GetMapping("/create")
    public Order createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        return orderService.createOrder(userId, productId);
    }

    @GetMapping("/seckill")
    @SentinelResource(value = "seckill-order", blockHandler = "seckillFallback")
    public Order secKillOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        return orderService.createOrder(userId, productId);
    }

    public Order seckillFallback(Long userId, Long productId, Throwable e) {
        log.error("seckill order failed");
        Order order = new Order();
        order.setId(productId);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setUserId(userId);
        order.setNickName("兜底回调用户");
        order.setAddress("异常信息：" + e.getClass());
        return order;
    }

    @GetMapping("/write")
    public String writeOrder() {
        return "write succeed ...";
    }

    @GetMapping("/read")
    public String readOrder() {
        log.info("read succeed ...");
        return "read succeed ...";
    }
}
