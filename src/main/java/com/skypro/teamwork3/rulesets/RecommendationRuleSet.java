package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.dto.RecommendationDTO;

import java.util.List;

public interface RecommendationRuleSet {
    List<RecommendationDTO> getRecommendation(String userId);
}
