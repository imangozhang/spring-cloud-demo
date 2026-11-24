# 第 3 课 MySQL 怎么找到商品

上一课看到 JDBC 把带参数的商品查询交给 MySQL。这一课往数据库里再走一步，先看实际接口用的 SKU 唯一索引，再用同一个商品场景观察筛选和排序怎样利用联合索引。

## 跑实验

先按 [第 2 课](02-jdbc.md) 的要求准备 Docker 和 Compose。在仓库目录执行以下命令。本课需要数据库，应用可以停着。

```bash
git fetch --tags
git switch --detach lesson-03
docker compose up -d --wait
docker compose exec -T mysql sh -c 'MYSQL_PWD="$MYSQL_PASSWORD" mysql -u "$MYSQL_USER" "$MYSQL_DATABASE"' \
  < db/lesson-03-indexes.sql
```

脚本先拿 `JdbcProductCatalog` 里的 SKU 查询做 `EXPLAIN`，再在同一个连接中创建一张临时商品表。这个实验不会改变接口使用的 `pms_product` 表。

## 看计划

1. 在 SKU 查询的计划中找到 `key`。它应指向 `product_sn` 唯一索引。课程中的 `id` 是主键，SKU 是单独的唯一键。
2. 对比临时表加索引前后的计划。先看 `key` 是否选中 `idx_category_price_id`，再看 `type`、估算扫描行数 `rows` 和 `Extra` 里的排序信息。执行计划可能随 MySQL 版本或统计信息变化。
3. 对照最后的 `COUNT(*)`。这组数据有 25 条符合条件。结果行数与计划估算要分开理解。

联合索引的前三列依次是类别、价格、商品 ID。先固定类别，再在这个类别内扫价格范围，最后按价格和商品 ID 的顺序取结果。试着把 `WHERE` 改成只按价格筛选，观察同一个索引能帮上多少忙。

实验跑完后，继续读 [飞书《MySQL 必知必会》](https://fbp63u3xtb.feishu.cn/wiki/Cqx1wF2Hkix88dkvrzOcfHRgnZg) 的请求处理、聚簇索引、二级索引和联合索引。

## 参考文献

- [MySQL 参考手册 · EXPLAIN Output Format](https://dev.mysql.com/doc/refman/8.4/en/explain-output.html)
- [MySQL 参考手册 · Multiple-Column Indexes](https://dev.mysql.com/doc/refman/8.4/en/multiple-column-indexes.html)
