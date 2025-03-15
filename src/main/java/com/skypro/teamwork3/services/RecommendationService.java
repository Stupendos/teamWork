package com.skypro.teamwork3.services;

import com.skypro.teamwork3.converter.RecommendationConverter;
import com.skypro.teamwork3.model.DynamicRule;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.jpa.repository.DynamicRuleRepository;
import com.skypro.teamwork3.rulesets.RecommendationRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> ruleSets;
    private final RuleValidationService ruleValidationService;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final RecommendationConverter recommendationConverter;

    @Autowired
    public RecommendationService(List<RecommendationRuleSet> ruleSets,
                                 RuleValidationService ruleValidationService,
                                 DynamicRuleRepository dynamicRuleRepository,
                                 RecommendationConverter recommendationConverter) {
        this.ruleSets = ruleSets;
        this.ruleValidationService = ruleValidationService;
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.recommendationConverter = recommendationConverter;
    }

    public List<Recommendation> getRecommendations(String userId) {
        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.addAll(
                ruleSets.stream()
                        .map(rule -> rule.getRecommendation(userId))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList())
        );

        List<DynamicRule> dynamicRules = dynamicRuleRepository.findAll();

        for (DynamicRule rule : dynamicRules) {
            boolean ruleValid = ruleValidationService.validateRule(userId, rule);
            if (ruleValid) {
                recommendations.add(recommendationConverter.convert(rule));
            }
        }
        return recommendations;
    }
}
