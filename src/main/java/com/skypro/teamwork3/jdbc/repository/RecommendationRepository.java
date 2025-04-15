package com.skypro.teamwork3.jdbc.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RecommendationRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean hasProductOfType(String userId, String type) {
        String query = """
                    SELECT EXISTS (
                        SELECT 1
                        FROM TRANSACTIONS t
                        LEFT JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                        WHERE t.USER_ID = ? AND p.TYPE = ?
                    )
                """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, new Object[]{userId, type}, Boolean.class));
    }

    public double getTotalDepositByType(String userId, String transactionType, String productType) {
        String query = """
                    SELECT COALESCE(SUM(t.AMOUNT), 0)
                    FROM TRANSACTIONS t
                    LEFT JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND t.TYPE = ? AND p.TYPE = ?
                """;
        return jdbcTemplate.queryForObject(query, new Object[]{userId, transactionType, productType}, Double.class);
    }

    public Integer amountOfTransactions(String userId, String productType) {
        String query = """
                SELECT COUNT(*) FROM TRANSACTIONS t
                LEFT JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                WHERE t.USER_ID = ? AND t.TYPE = ?
                """;
        Integer count = jdbcTemplate.queryForObject(query, new Object[]{userId, productType}, Integer.class);
        return count;
    }

    public String getIdByUsername(String username) {
        try {
            String query = """
                    SELECT ID
                    FROM USERS
                    WHERE USERNAME = ?
                    """;
            return jdbcTemplate.queryForObject(query, new Object[]{username}, String.class);
        } catch (Exception e) {
            return "";
        }
    }
}
