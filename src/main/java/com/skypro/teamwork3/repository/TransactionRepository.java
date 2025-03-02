package com.skypro.teamwork3.repository;

import com.skypro.teamwork3.model.Product;
import com.skypro.teamwork3.model.UserTransactionData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private RowMapper<UserTransactionData> transactionRowMapper() {
        return (rs, rowNum) -> new UserTransactionData(
                rs.getString("ID"),
                rs.getString("USER_ID"),
                rs.getString("PRODUCT_ID"),
                rs.getString("TYPE"),
                rs.getBigDecimal("AMOUNT")
        );
    }

    public List<UserTransactionData> getTransactionsByUserId(String userId) {
        String sql = """
                    SELECT t.ID, t.USER_ID, t.PRODUCT_ID, t.TYPE, t.AMOUNT
                    FROM TRANSACTIONS t
                    WHERE t.user_id = ?
                """;
        return jdbcTemplate.query(sql, transactionRowMapper(), userId);
    }
}
