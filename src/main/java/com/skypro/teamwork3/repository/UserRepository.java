package com.skypro.teamwork3.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean hasProductOfType(String userId, String type) {
        String query = """
                    SELECT EXISTS (
                        SELECT 1
                        FROM TRANSACTIONS t
                        JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                        WHERE t.USER_ID = ? AND p.TYPE = ?
                    )
                """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, new Object[]{userId, type}, Boolean.class));
    }

    public double getTotalDepositByType(String userId, String productType) {
        String query = """
                    SELECT COALESCE(SUM(t.AMOUNT), 0)
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND t.TYPE = 'DEPOSIT' AND p.TYPE = ?
                """;
        return jdbcTemplate.queryForObject(query, new Object[]{userId, productType}, Double.class);
    }

    public double getTotalWithdraw(String userId, String productType) {
        String query = """
                    SELECT COALESCE(SUM(t.AMOUNT), 0)
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND t.TYPE = 'WITHDRAW' AND p.TYPE = ?
                """;
        return jdbcTemplate.queryForObject(query, new Object[]{userId, productType}, Double.class);
    }
}
