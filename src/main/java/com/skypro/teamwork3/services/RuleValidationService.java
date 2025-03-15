package com.skypro.teamwork3.services;

import com.skypro.teamwork3.model.DynamicRule;
import com.skypro.teamwork3.jpa.repository.DynamicRuleRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleValidationService {
    private final DynamicRuleRepository dynamicRuleRepository;

    public RuleValidationService(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    private boolean checkUserOf(String userId, DynamicRule dynamicRule) {
        String productType = dynamicRule.getArguments().get(0);
        boolean negate = dynamicRule.isNegate();

        boolean hasTransactions = dynamicRuleRepository.existsByUserIdAndProductType(userId, productType);
        return negate ? !hasTransactions : hasTransactions;
    }

    private boolean checkActiveUserOf(String userId, DynamicRule dynamicRule) {
        String productType = dynamicRule.getArguments().get(0);
        boolean negate = dynamicRule.isNegate();
        int transactionCount = dynamicRuleRepository.countByUserIdAndProductType(userId, productType);
        boolean isActive = transactionCount > 5;

        return negate ? !isActive : isActive;
    }

    private boolean checkTransactionSumCompare(String userId, DynamicRule dynamicRule) {
        String productType = dynamicRule.getArguments().get(0);
        String transactionType = dynamicRule.getArguments().get(1);
        String operator = dynamicRule.getArguments().get(2);
        int treshold = Integer.parseInt(dynamicRule.getArguments().get(3));
        boolean negate = dynamicRule.isNegate();

        int transactionSum = dynamicRuleRepository.sumByUserIdAndProdyctTypeAndTransactionType(userId, productType, transactionType);

        boolean comparisonResult = compare(transactionSum, operator, treshold);
        return negate ? !comparisonResult : comparisonResult;
    }

    private boolean compare(int value, String operator, int threshold) {
        switch (operator) {
            case ">":
                return value > threshold;
            case "<":
                return value < threshold;
            case "=":
                return value == threshold;
            case ">=":
                return value >= threshold;
            case "<=":
                return value <= threshold;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private boolean checkTransactionSumCompareDepositWithdraw(String userId, DynamicRule dynamicRule) {
        String productType = dynamicRule.getArguments().get(0);
        String operator = dynamicRule.getArguments().get(1);
        boolean negate = dynamicRule.isNegate();

        int depositSum = dynamicRuleRepository.sumByUserIdAndProdyctTypeAndTransactionType(userId, productType, "DEPOSIT");
        int withdrawSum = dynamicRuleRepository.sumByUserIdAndProdyctTypeAndTransactionType(userId, productType, "WITHDRAW");

        boolean comparisonResult = compare(depositSum - withdrawSum, operator, 0);
        return negate ? !comparisonResult : comparisonResult;
    }

    public boolean validateRule(String userId, DynamicRule dynamicRule) {
        switch (dynamicRule.getQuery()) {
            case "USER_OF":
                return checkUserOf(userId, dynamicRule);
            case "ACTIVE_USER_OF":
                return checkActiveUserOf(userId, dynamicRule);
            case "TRANSACTION_SUM_COMPARE":
                return checkTransactionSumCompare(userId, dynamicRule);
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW":
                return checkTransactionSumCompareDepositWithdraw(userId, dynamicRule);
            default:
                throw new IllegalArgumentException("Unknown query: " + dynamicRule.getQuery());
        }
    }
}
