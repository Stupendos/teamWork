package com.skypro.teamwork3.services;

import com.skypro.teamwork3.jdbc.repository.RecommendationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class RuleValidationServiceTest {
    @Mock
    private RecommendationRepository recommendationRepository;

    @InjectMocks
    private RuleValidationService ruleValidationService;

    @Test
    void evaluateUserOfRule() {
        String userId = "user123";
        String productType = "CREDIT";
        boolean negate = false;

        Mockito.when(recommendationRepository.hasProductOfType(userId, productType)).thenReturn(true);

        boolean result = ruleValidationService.evaluateUserOfRule(userId, productType, negate);

        Assertions.assertTrue(result);
        Mockito.verify(recommendationRepository, Mockito.times(1)).hasProductOfType(userId, productType);

    }

    @Test
    void evaluateActiveUserOfRule() {
        String userId = "user123";
        String productType = "CREDIT";
        boolean negate = false;

        Mockito.when(recommendationRepository.amountOfTransactions(userId, productType)).thenReturn(5);

        boolean result = ruleValidationService.evaluateActiveUserOfRule(userId, productType, negate);

        Assertions.assertTrue(result);
        Mockito.verify(recommendationRepository, Mockito.times(1)).amountOfTransactions(userId, productType);
    }

    @Test
    void evaluateTransactionSumCompareRule() {
        String userId = "user123";
        String productType = "INVEST";
        String transactionType = "DEPOSIT";
        String operator = ">";
        int value = 1000;
        boolean negate = false;

        Mockito.when(recommendationRepository.getTotalDepositByType(userId, transactionType, productType)).thenReturn(1500.0);

        boolean result = ruleValidationService.evaluateTransactionSumCompareRule(userId, productType, transactionType, operator, value, negate);

        Assertions.assertTrue(result);
        Mockito.verify(recommendationRepository, Mockito.times(1)).getTotalDepositByType(userId, transactionType, productType);
    }

    @Test
    void evaluateTransactionSumCompareDepositWithdrawRule() {
        String userId = "user123";
        String productType = "CREDIT";
        String operator = "<";
        boolean negate = false;
        Mockito.when(recommendationRepository.getTotalDepositByType(userId, "DEPOSIT", productType))
                .thenReturn(500.0);
        Mockito.when(recommendationRepository.getTotalDepositByType(userId, "WITHDRAW", productType))
                .thenReturn(300.0);

        boolean result = ruleValidationService.evaluateTransactionSumCompareDepositWithdrawRule(userId, productType, operator, negate);

        assertFalse(result);
    }
}