package com.greenbook.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pay-client", url = "https://api.mch.weixin.qq.com")
public interface PayFeignClient {

    @PostMapping("/v3/pay/transactions/jsapi")
    String pay(@RequestHeader("Authorization") String authorization,
               @RequestParam("appid") String appid,
               @RequestParam("mchid") String mchid);
}
