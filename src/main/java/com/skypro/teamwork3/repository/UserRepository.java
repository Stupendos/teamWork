package com.skypro.teamwork3.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getProductTypes(String userId) {
        String query = "SELECT TYPE FROM TRANSACTIONS WHERE USER_ID = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("TYPE"), userId);
    }

    public Map<String, Double> getProductBalances(String userId) {
        String query = "SELECT AMOUNT FROM TRANSACTIONS WHERE USER_ID = ? AND TYPE = 'DEPOSIT' GROUP BY AMOUNT";
        return jdbcTemplate.query(query, rs -> {
            Map<String, Double> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString("TYPE"), rs.getDouble("AMOUNT"));
            }
            return map;
        }, userId);
    }

    public Map<String, Double> getProductExpenses(String userId) {
        String query = "SELECT SUM(AMOUNT) FROM TRANSACTIONS WHERE USER_ID = ? AND TYPE = 'WITHDRAW' GROUP BY TYPE";
        return jdbcTemplate.query(query, rs -> {
            Map<String, Double> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString("TYPE"), rs.getDouble("AMOUNT"));
            }
            return map;
        }, userId);
    }
}
