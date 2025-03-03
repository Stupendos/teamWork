package com.skypro.teamwork3.services;

import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.rulesets.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService{
    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    public List<Recommendation> getRecommendations(String userId) {
        return ruleSets.stream()
                .map(rule -> rule.getRecommendation(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
//    public Map<String, Object> getRecommendations(String userId) {
//        List<Recommendation> recommendations = new ArrayList<>();
//        for (RecommendationRuleSet ruleSet : ruleSets) {
//            Optional<Recommendation> recommendation = ruleSet.getRecommendation(userId);
//        }
//        Map<String, Object> response = new HashMap<>();
//        response.put("user_id", userId);
//        response.put("recommendations", recommendations);
//        return response;
//
//    }

}
