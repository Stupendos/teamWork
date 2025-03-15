package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.model.UserTransactionData;

import java.util.Optional;

public interface RecommendationRuleSet {
    Optional<Recommendation> getRecommendation(String userId);
}
