package com.skypro.teamwork3.jpa.repository;

import com.skypro.teamwork3.model.DynamicRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {
    List<DynamicRule> findByRecommendationId(String recommendationId);

    boolean existsByUserIdAndProductType(String userId, String productType);

    int countByUserIdAndProductType(String userId, String productType);

    int sumByUserIdAndProdyctTypeAndTransactionType(String userId, String productType, String transactionType);
}
