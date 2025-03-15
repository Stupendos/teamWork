package com.skypro.teamwork3.repository;

import com.skypro.teamwork3.model.Product;
import com.skypro.teamwork3.model.UserTransactionData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserTransactionData> getUserTransactions(String userId) {
        String sql = "SELECT * FROM TRANSACTIONS WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) ->
                new UserTransactionData(
                        rs.getString("ID"),
                        rs.getString("USER_ID"),
                        rs.getString("PRODUCT_ID"),
                        rs.getString("TYPE"),
                        rs.getBigDecimal("AMOUNT")
                )
        );
    }

    public List<Product> getProducts() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Product(
                        rs.getString("ID"),
                        rs.getString("TYPE"),
                        rs.getString("NAME")
                )
        );
    }
}
