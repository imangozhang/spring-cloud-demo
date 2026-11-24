# 第 2 课 用 JDBC 查商品

第 1 课的商品数据写在 Java 类里。现在让同一条查询从 HTTP 接口经过 `JdbcProductCatalog`，到 MySQL 的 `pms_product` 表，再把结果放回 JSON。接口地址、返回字段和订单预览的算法沿用上一课。

## 跑起来

在已有的仓库里执行以下命令。第一次跟做时，从 [第 1 课](01-spring-boot.md) 获取克隆与环境准备命令。这里还需要 Docker 和 Docker Compose v2。

```bash
git fetch --tags
git switch --detach lesson-02
docker compose up -d --wait
mvn spring-boot:run
```

Compose 会在第一次初始化数据卷时执行 `db/init.sql`。MySQL 默认只绑定本机 3307，应用使用示例账号。如果已有本地 MySQL，也可以自行建库、导入同一份 SQL，再通过 `JDBC_URL`、`JDBC_USER`、`JDBC_PASSWORD` 指向它。

另开一个终端，重复第 1 课的查询。

```bash
curl -i http://localhost:8080/api/products/by-sku/s10000678
curl -i http://localhost:8080/api/products/by-sku/missing-sku
curl -i 'http://localhost:8080/api/orders/preview?productId=1&quantity=2'
```

预期依次是 200、404、200。第三个请求仍然返回 9.00。现在改动数据库里的价格，两个读取接口都会看到新的值。改完之后可以把价格恢复成 4.50。

```bash
docker compose exec -T mysql sh -c 'MYSQL_PWD="$MYSQL_PASSWORD" mysql -u "$MYSQL_USER" "$MYSQL_DATABASE"' <<'SQL'
UPDATE pms_product SET price = 5.00 WHERE id = 1;
SQL
```

## 跟一遍代码

`ProductController` 调用 `ProductCatalog.findBySku()`，Spring 注入本课的 JDBC 实现。看 `JdbcProductCatalog.findOne()` 如何借连接、绑定第一个问号、执行查询，再用 `ResultSet.next()` 判断是否读到记录。没有结果时是 404，数据库异常会沿调用链返回 500。订单预览按数值 ID 走同一个仓储实现。

想看请求在驱动里怎么编码、结果包怎么读，可以读 [飞书《Java 程序是如何执行 SQL 的》](https://fbp63u3xtb.feishu.cn/wiki/V1lowBr4piZ7YNkRxNBcl7g1nBd)。

做完可以用 `docker compose stop` 停止数据库，数据卷会留给下一课。

## 参考文献

- [Oracle Java 教程 · Processing SQL Statements with JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html)
- [Oracle Java 教程 · Using Prepared Statements](https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html)
