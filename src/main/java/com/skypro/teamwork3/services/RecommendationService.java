package com.skypro.teamwork3.services;

import com.skypro.teamwork3.dto.*;
import com.skypro.teamwork3.jpa.repository.DynamicRecommendationRepository;
import com.skypro.teamwork3.jpa.repository.DynamicRuleRepository;
import com.skypro.teamwork3.model.DynamicRule;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.rulesets.DynamicRuleSet;
import com.skypro.teamwork3.rulesets.RecommendationRuleSet;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final List<RecommendationRuleSet> ruleSets;
    private final DynamicRecommendationRepository dynamicRecommendationRepository;
    private final DynamicRuleSet dynamicRuleSet;
    private final DynamicRuleRepository dynamicRuleRepository;


    public RecommendationService(List<RecommendationRuleSet> ruleSets,
                                 DynamicRecommendationRepository dynamicRecommendationRepository,
                                 DynamicRuleSet dynamicRuleSet, DynamicRuleRepository dynamicRuleRepository) {
        this.ruleSets = ruleSets;
        this.dynamicRecommendationRepository = dynamicRecommendationRepository;
        this.dynamicRuleSet = dynamicRuleSet;
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    public List<RecommendationDTO> getRecommendations(String userId) {
        List<RecommendationDTO> recommendationDTOs = new ArrayList<>();
        for (RecommendationRuleSet ruleSet : ruleSets) {
            List<RecommendationDTO> ruleRecommendations = ruleSet.getRecommendation(userId);
            if (!ruleRecommendations.isEmpty()) {
                recommendationDTOs.addAll(ruleRecommendations);
            }
        }
        return recommendationDTOs;
    }

    public Recommendation createRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = new Recommendation();
        recommendation.setRecommendationId(recommendationDTO.getRecommendationId());
        recommendation.setName(recommendationDTO.getName());
        recommendation.setText(recommendationDTO.getDescription());
        dynamicRecommendationRepository.save(recommendation);

        List<DynamicRule> dynamicRules = new ArrayList<>();
        for (DynamicRuleDTO dynamicRuleDTO : recommendationDTO.getDynamicRules()) {
            DynamicRule dynamicRule = new DynamicRule();
            dynamicRule.setQuery(dynamicRuleDTO.getQuery());
            dynamicRule.setArguments(dynamicRuleDTO.getArguments());
            dynamicRule.setNegate(dynamicRuleDTO.isNegate());

            dynamicRule.setRecommendation(recommendation);

            dynamicRules.add(dynamicRule);

        }
        dynamicRuleRepository.saveAll(dynamicRules);
        recommendation.setDynamicRules(dynamicRules);
        return recommendation;
    }

    @Transactional
    public List<Recommendation> getAllRecommendations() {
        List<Recommendation> recommendations = dynamicRecommendationRepository.findAll();
        for (Recommendation recommendation : recommendations) {
            Hibernate.initialize(recommendation.getDynamicRules());
        }
        return recommendations;
    }

    @Transactional
    public void deleteRecommendation(Long id) {
        Optional<Recommendation> recommendation = dynamicRecommendationRepository.findById(id);
        if (recommendation.isPresent()) {
            dynamicRecommendationRepository.delete(recommendation.get());
        } else {
            new RuntimeException("Recommendation with id " + id + " not found");
        }
    }
}
