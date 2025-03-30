package com.skypro.teamwork3.services;

import com.skypro.teamwork3.jdbc.repository.RecommendationRepository;
import com.skypro.teamwork3.model.DynamicRule;
import com.skypro.teamwork3.jpa.repository.DynamicRuleRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleValidationService {
    private final RecommendationRepository recommendationRepository;

    public RuleValidationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public boolean evaluateForQuery(DynamicRule dynamicRule, String userId) {
        return switch (dynamicRule.getQuery()) {
            case USER_OF -> evaluateUserOfRule(userId, dynamicRule.getArguments().get(0), dynamicRule.isNegate());
            case ACTIVE_USER_OF ->
                    evaluateActiveUserOfRule(userId, dynamicRule.getArguments().get(0), dynamicRule.isNegate());
            case TRANSACTION_SUM_COMPARE -> evaluateTransactionSumCompareRule(userId,
                    dynamicRule.getArguments().get(0),
                    dynamicRule.getArguments().get(1),
                    dynamicRule.getArguments().get(2),
                    Integer.valueOf(dynamicRule.getArguments().get(3)),
                    dynamicRule.isNegate()
            );
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW -> evaluateTransactionSumCompareDepositWithdrawRule(
                    userId,
                    dynamicRule.getArguments().get(0),
                    dynamicRule.getArguments().get(1),
                    dynamicRule.isNegate()
            );
        };
    }

    public boolean evaluateUserOfRule(String userId, String productType, boolean negate) {
        boolean result = recommendationRepository.hasProductOfType(userId, productType);
        return negate ? !result : result;
    }

    public boolean evaluateActiveUserOfRule(String userId, String productType, boolean negate) {
        int transactionCount = recommendationRepository.amountOfTransactions(userId, productType);
        boolean result = transactionCount >= 5;
        return negate ? !result : result;
    }

    public boolean evaluateTransactionSumCompareRule(
            String userId, String productType, String transactionType, String operator, Integer value, boolean negate) {
        double totalSum = recommendationRepository.getTotalDepositByType(userId, transactionType, productType);

        boolean result = switch (operator) {
            case ">" -> totalSum > value;
            case "<" -> totalSum < value;
            case "=" -> totalSum == value;
            case ">=" -> totalSum >= value;
            case "<=" -> totalSum <= value;
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };
        return negate ? !result : result;
    }

    public boolean evaluateTransactionSumCompareDepositWithdrawRule(
            String userId, String productType, String operator, boolean negate) {
        double totalDeposit = recommendationRepository.getTotalDepositByType(userId, "DEPOSIT", productType);
        double totalWithdraw = recommendationRepository.getTotalDepositByType(userId, "WITHDRAW", productType);

        boolean result = switch (operator) {
            case ">" -> totalDeposit > totalWithdraw;
            case "<" -> totalDeposit < totalWithdraw;
            case "=" -> totalDeposit == totalWithdraw;
            case ">=" -> totalDeposit >= totalWithdraw;
            case "<=" -> totalDeposit <= totalWithdraw;
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };

        return negate ? !result : result;
    }
}
