package com.greenbook.order.feign;

import com.greenbook.order.feign.fallback.ProductFeignClientFallback;
import com.greenbook.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product", fallback = ProductFeignClientFallback.class)
public interface ProductFeignClient {

    // mvc 注解的两套逻辑：
    // 1.标注在 Controller 上，是接收请求
    // 2.标注在 FeignClient 上，是发送请求
    @GetMapping("/{id}")
    Product getProduct(@PathVariable("id") Long id);
}
