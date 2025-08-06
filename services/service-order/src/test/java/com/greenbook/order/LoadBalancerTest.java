package com.greenbook.order;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@SpringBootTest
public class LoadBalancerTest {
    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Test
    public void test() {
        String url = loadBalancerClient.choose("service-order").getUri().toString();
        System.out.println(url);

        url = loadBalancerClient.choose("service-order").getUri().toString();
        System.out.println(url);

        url = loadBalancerClient.choose("service-order").getUri().toString();
        System.out.println(url);

        url = loadBalancerClient.choose("service-order").getUri().toString();
        System.out.println(url);
    }
}
