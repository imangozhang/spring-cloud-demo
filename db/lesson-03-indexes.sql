-- 本脚本只在这次连接中创建临时表，结束后可以直接重跑。
EXPLAIN
SELECT id, product_sn, name, price
FROM pms_product
WHERE product_sn = 's10000678';

CREATE TEMPORARY TABLE lesson03_products (
    id BIGINT PRIMARY KEY,
    category_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
) ENGINE = InnoDB;

INSERT INTO lesson03_products (id, category_id, name, price)
WITH digits AS (
    SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3
    UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6
    UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9
), numbers AS (
    SELECT ones.n + tens.n * 10 + hundreds.n * 100 + thousands.n * 1000 AS n
    FROM digits AS ones CROSS JOIN digits AS tens
    CROSS JOIN digits AS hundreds CROSS JOIN digits AS thousands
)
SELECT n + 1, MOD(n, 100), CONCAT('商品-', n + 1), 4.50 + (n DIV 100) / 10
FROM numbers;

SELECT '加索引前' AS stage;

EXPLAIN
SELECT id, name, price
FROM lesson03_products
WHERE category_id = 42 AND price < 7.00
ORDER BY price, id;

SELECT '加索引后' AS stage;

ALTER TABLE lesson03_products ADD INDEX idx_category_price_id (category_id, price, id);

EXPLAIN
SELECT id, name, price
FROM lesson03_products
WHERE category_id = 42 AND price < 7.00
ORDER BY price, id;

SELECT COUNT(*) AS matching_rows
FROM lesson03_products
WHERE category_id = 42 AND price < 7.00;
