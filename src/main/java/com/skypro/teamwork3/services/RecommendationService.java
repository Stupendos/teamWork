package com.skypro.teamwork3.services;

import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.jdbc.repository.RecommendationRepository;
import com.skypro.teamwork3.jpa.repository.DynamicRecommendationRepository;
import com.skypro.teamwork3.jpa.repository.DynamicRuleRepository;
import com.skypro.teamwork3.model.DynamicRule;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.rulesets.DynamicRuleSet;
import com.skypro.teamwork3.rulesets.RecommendationRuleSet;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> ruleSets;
    private final RecommendationRepository recommendationRepository;
    private final DynamicRecommendationRepository dynamicRecommendationRepository;
    private final DynamicRuleSet dynamicRuleSet;


    @Autowired
    public RecommendationService(List<RecommendationRuleSet> ruleSets,
                                 RecommendationRepository recommendationRepository,
                                 DynamicRecommendationRepository dynamicRecommendationRepository,
                                 DynamicRuleSet dynamicRuleSet) {
        this.ruleSets = ruleSets;
        this.recommendationRepository = recommendationRepository;
        this.dynamicRecommendationRepository = dynamicRecommendationRepository;
        this.dynamicRuleSet = dynamicRuleSet;
    }

    public List<RecommendationDTO> getRecommendations(String userId) {
        List<RecommendationDTO> recommendationDTOs = new ArrayList<>();
        for (RecommendationRuleSet ruleSet : ruleSets) {
            List<RecommendationDTO> ruleRecommendations = ruleSet.getRecommendation(userId);
            if (recommendationDTOs != null && !ruleRecommendations.isEmpty()) {
                recommendationDTOs.addAll(ruleRecommendations);
            }
        }
        return recommendationDTOs;
    }

    public Recommendation createRecommendation(UUID recommendationId, String name, String text, List<DynamicRule> dynamicRules) {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(recommendationId);
        recommendation.setName("Some name");
        recommendation.setText(text);
        recommendation.setDynamicRules(dynamicRules);

        dynamicRecommendationRepository.save(recommendation);

        System.out.println(recommendation.toString());
        return recommendation;

    }

}
