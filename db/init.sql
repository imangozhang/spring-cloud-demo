CREATE TABLE IF NOT EXISTS pms_product (
    id BIGINT PRIMARY KEY,
    product_sn VARCHAR(32) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

INSERT IGNORE INTO pms_product (id, product_sn, name, price)
VALUES (1, 's10000678', '红牛', 4.50);
