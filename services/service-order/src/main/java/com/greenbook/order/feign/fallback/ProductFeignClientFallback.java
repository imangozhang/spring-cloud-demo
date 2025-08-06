package com.greenbook.order.feign.fallback;

import com.greenbook.order.feign.ProductFeignClient;
import com.greenbook.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ProductFeignClientFallback implements ProductFeignClient {

    @Override
    public Product getProduct(Long id) {
        log.info("兜底回调...");
        Product product = new Product();
        // 这里可以根据项目需求填充默认值或其他处理逻辑
        product.setId(id);
        product.setProductName("产品名称");
        product.setPrice(BigDecimal.ZERO);
        product.setNum(0);
        return product;
    }
}
