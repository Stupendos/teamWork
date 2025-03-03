package com.skypro.teamwork3.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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
                        FROM TRANSACTIONS 
                        WHERE USER_ID = ? AND TYPE = ?
                    )
                """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, new Object[]{userId, type}, Boolean.class));
    }

        public double getTotalDepositByType (String userId, String type){
            String query = """
                        SELECT COALESCE(SUM(AMOUNT), 0) 
                        FROM TRANSACTIONS 
                        WHERE USER_ID = ? AND TYPE = ?
                    """;
            return jdbcTemplate.queryForObject(query, new Object[]{userId, type}, Double.class);
        }

    public double getTotalWithdrawalByType(String userId, String type) {
        String query = """
            SELECT COALESCE(SUM(AMOUNT), 0) 
            FROM TRANSACTIONS 
            WHERE USER_ID = ? AND TYPE = ?
        """;
        return jdbcTemplate.queryForObject(query, new Object[]{userId, type}, Double.class);
    }

//
        public Map<String, Double> getProductExpenses (String userId){
            String query = """
                        SELECT TYPE, SUM(AMOUNT) AS TOTAL_AMOUNT 
                        FROM TRANSACTIONS 
                        WHERE USER_ID = ? 
                        AND TYPE = 'WITHDRAW' 
                        GROUP BY TYPE
                    """;

            return jdbcTemplate.query(query, new Object[]{userId}, rs -> {
                Map<String, Double> map = new HashMap<>();
                while (rs.next()) {
                    map.put(rs.getString("TYPE"), rs.getDouble("TOTAL_AMOUNT"));
                }
                return map;
            });
        }
    }
