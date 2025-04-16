package com.skypro.teamwork3.jdbc.repository;

import com.skypro.teamwork3.exceptions.UsernameDontExistException;
import com.skypro.teamwork3.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Types;

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
        String query = """
                SELECT ID
                FROM USERS
                WHERE USERNAME = ?
                """;
        return jdbcTemplate.queryForObject(query, new Object[]{username}, String.class);
    }

    public User getAllByUsername(String username) {
        String query = """
                SELECT *
                FROM USERS
                WHERE USERNAME = ?
                """;
        SqlRowSet row = jdbcTemplate.queryForRowSet(query, username);
        row.next(); //да, эта штука тут нужна, даже если ряд только один
        return new User(row.getString("ID"), row.getString("USERNAME"), row.getString("FIRST_NAME"), row.getString("LAST_NAME"));
    }
}
