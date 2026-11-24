# 从商品查询到订单服务

《Java 面试小绿书》配套项目。从能运行的商品查询起步，沿着同一个订单案例逐步增加能力。每课都固定在一个 Git 标签上，可以独立复现。

| 课次 | 动手目标 | 代码 |
| --- | --- | --- |
| 01 | 用 Spring Boot 跑起商品查询和订单预览 | [lesson-01](../../tree/lesson-01) |
| 02 | 保留接口，把内存数据换成 MySQL | [lesson-02](../../tree/lesson-02) |
| 03 | 用执行计划看商品查询如何走索引 | [lesson-03](../../tree/lesson-03) |

从 [第 1 课](lessons/01-spring-boot.md) 开始跑接口，接着做 [JDBC 实验](lessons/02-jdbc.md) 和 [MySQL 索引实验](lessons/03-mysql-indexes.md)。

原 Spring Cloud 完整项目在 [dev 分支](../../tree/dev)，包括原来配套 Nacos、OpenFeign、Sentinel、Gateway 和 Seata 的示例。

后续课程将沿用这个商品与订单案例，分别讲缓存、消息、服务发现与远程调用、限流熔断、网关和分布式事务。当前可按标签复现的是上表三课。
