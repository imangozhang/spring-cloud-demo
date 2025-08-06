package com.greenbook.order;

import com.greenbook.order.feign.PayFeignClient;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FeignClientTest {

    @Resource
    private PayFeignClient payFeignClient;

    @Test
    void payTest() {
        String authorization = "WECHATPAY2-SHA256-RSA2048 mchid=\\\"1900000001\\\",...";
        String appId = "wx1234567890123456";
        String mchId = "1900000001";
        String prepayId = payFeignClient.pay(authorization, appId, mchId);
        System.out.println(prepayId);
    }
}
