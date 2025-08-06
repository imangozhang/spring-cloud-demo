package com.greenbook.product;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@SpringBootTest
public class DiscoveryTest {

    @Resource
    DiscoveryClient discoveryClient;

    @Test
    void discoveryClientTest() {

        // 获取所有微服务
        for (String service : discoveryClient.getServices()) {
            System.out.println("service = " + service);

            // 获取每个服务的 IP + Port
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                System.out.println(" - " + instance.getHost() + ":" + instance.getPort());
            }
        }
    }
}
