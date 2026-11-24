# 从商品查询到订单服务

《Java 面试小绿书》配套项目。我们先把商品查询和订单预览放在同一个 Spring Boot 应用里跑通，再按课次给它接上数据库。每课用 Git 标签固定一份完整代码，可以从第一课开始跟做，也能直接进入对应实验。

原 Spring Cloud 完整项目在 [dev 分支](https://github.com/imangozhang/spring-cloud-demo/tree/dev)，保留了原来的 Nacos、OpenFeign、Sentinel、Gateway 和 Seata 示例。

## 第 1 课 跑起第一个接口

准备 JDK 17 或更新版本，以及 Maven 3.6.3 或更新版本。

```bash
git clone https://github.com/imangozhang/spring-cloud-demo.git
cd spring-cloud-demo
git switch --detach lesson-01
mvn spring-boot:run
```

另开一个终端，按下面的顺序试一次。

```bash
curl -i http://localhost:8080/api/products/1
curl -i http://localhost:8080/api/products/by-sku/s10000678
curl -i 'http://localhost:8080/api/orders/preview?productId=1&quantity=2'
curl -i http://localhost:8080/api/products/999
curl -i 'http://localhost:8080/api/orders/preview?productId=1&quantity=0'
```

前两个请求返回同一件商品。第三个返回订单预览，单价为 4.50，总额为 9.00。最后两个分别返回 404 和 400。订单预览只计算金额，适合用 GET 请求。

从 `ShopApplication` 启动后，先读 `ProductController` 和 `OrderController`，再看它们共同依赖的 `ProductCatalog`。试着修改 `InMemoryProductCatalog` 的价格，重启后重新查商品和预览订单，观察两处结果如何变化。下一课把内存实现换成 MySQL，接口地址和返回字段沿用这一课。

参考 [Spring 官方 REST 服务入门](https://spring.io/guides/gs/rest-service)。
