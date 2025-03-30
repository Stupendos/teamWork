package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.jpa.repository.DynamicRecommendationRepository;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.services.RuleValidationService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DynamicRuleSet implements RecommendationRuleSet {
    private final RuleValidationService ruleValidationService;
    private final DynamicRecommendationRepository dynamicRecommendationRepository;

    public DynamicRuleSet(RuleValidationService ruleValidationService, DynamicRecommendationRepository dynamicRecommendationRepository) {
        this.ruleValidationService = ruleValidationService;
        this.dynamicRecommendationRepository = dynamicRecommendationRepository;
    }


    @Override
    public List<RecommendationDTO> getRecommendation(String userId) {
        return dynamicRecommendationRepository.findAll().stream()
                .map(recommendation -> processRecommendation(recommendation, userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<RecommendationDTO> processRecommendation(Recommendation recommendation, String userId) {
        Boolean isOk = recommendation.getDynamicRules().stream()
                .map(dynamicRule -> ruleValidationService.evaluateForQuery(dynamicRule, userId))
                .reduce(true, (a, b) -> a && b);

        if (isOk) {
            return Optional.of(new RecommendationDTO(
                    recommendation.getRecommendationId(),
                    recommendation.getName(),
                    recommendation.getText()
            ));
        } else {
            return Optional.empty();
        }
    }
}
