package com.greenbook.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductCatalog implements ProductCatalog {

    private static final String BY_ID = "SELECT id, product_sn, name, price FROM pms_product WHERE id = ?";
    private static final String BY_SKU = "SELECT id, product_sn, name, price FROM pms_product WHERE product_sn = ?";

    private final DataSource dataSource;

    public JdbcProductCatalog(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Product> findById(long id) {
        return findOne(BY_ID, statement -> statement.setLong(1, id));
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return findOne(BY_SKU, statement -> statement.setString(1, sku));
    }

    private Optional<Product> findOne(String sql, Parameter parameter) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            parameter.bind(statement);
            try (ResultSet rows = statement.executeQuery()) {
                if (!rows.next()) {
                    return Optional.empty();
                }
                return Optional.of(new Product(
                        rows.getLong("id"),
                        rows.getString("product_sn"),
                        rows.getString("name"),
                        rows.getBigDecimal("price")));
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("读取商品数据失败", exception);
        }
    }

    @FunctionalInterface
    private interface Parameter {

        void bind(PreparedStatement statement) throws SQLException;
    }
}
