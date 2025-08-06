package com.greenbook.order.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderServiceConfig {

    @LoadBalanced   // 注解式负载均衡
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 日志级别
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /*@Bean
    Retryer retryer() {
        return new Retryer.Default();
    }*/
}
