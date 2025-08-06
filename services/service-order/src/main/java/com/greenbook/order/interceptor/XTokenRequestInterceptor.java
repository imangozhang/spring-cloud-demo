package com.greenbook.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class XTokenRequestInterceptor implements RequestInterceptor {

    /**
     * 拦截器，添加X-Token头信息
     * @param requestTemplate 请求模板
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("进入拦截器，添加 X-Token 头信息");
        requestTemplate.header("X-Token", UUID.randomUUID().toString());
    }
}
