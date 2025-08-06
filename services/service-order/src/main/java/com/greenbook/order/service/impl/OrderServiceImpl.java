package com.greenbook.order.service.impl;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.greenbook.order.Order;
import com.greenbook.order.feign.ProductFeignClient;
import com.greenbook.order.service.OrderService;
import com.greenbook.product.Product;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private ProductFeignClient productFeignClient;

    @SentinelResource(value = "createOrder", blockHandler = "createOrderBlockHandler")
    @Override
    public Order createOrder(Long userId, Long productId) {
        Product product = getProductRemoteLoadBalanceFeign(productId);

        Order order = new Order();
        order.setId(0L);
        order.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(product.getNum())));
        order.setUserId(userId);
        order.setNickName("greenbook");
        order.setAddress("中关村大街10号院");
        order.setProducts(List.of(product));
        return order;
    }

    // 执行兜底回调
    public Order createOrderBlockHandler(Long userId, Long productId, BlockException e) {
        Order order = new Order();
        order.setId(0L);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setUserId(userId);
        order.setNickName("未知用户");
        order.setAddress("异常信息：" + e.getClass());
        return order;
    }

    // 版本1: 直接调用版本
    private Product getProductRemote(Long productId) {
        // 1.获取商品服务的 IP + Port
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance instance = instances.get(0);

        // 2.获取商品服务的 URL: "http://localhost:8081/product/1"
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/product/" + productId;
        System.out.println("远程调用：" + url);

        // 3.调用商品服务的接口，获取商品信息
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }

    // 版本2: 负载均衡版本
    private Product getProductRemoteLoadBalance(Long productId) {
        // 1.通过负载均衡获取商品服务的 IP + Port
        ServiceInstance instance = loadBalancerClient.choose("service-product");

        // 2.获取商品服务的 URL: "http://localhost:8081/product/1"
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/product/" + productId;
        System.out.println("远程调用：" + url);
        // 3.调用商品服务的接口，获取商品信息
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }

    // 版本3: 注解式负载均衡
    private Product getProductRemoteLoadBalanceAnnotation(Long productId) {
        // 1.获取商品服务的 URL: "http://localhost:8081/product/1"
        String url = "http://service-product/product/" + productId;

        // 2.调用商品服务的接口，获取商品信息
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }

    // 版本4: 注解式负载均衡 + FeignClient
    private Product getProductRemoteLoadBalanceFeign(Long productId) {
        // 1.通过负载均衡获取商品服务的 IP + Port
        Product product = productFeignClient.getProduct(productId);
        // 2.返回商品信息
        return product;
    }
}
