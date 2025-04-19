package com.skypro.teamwork3.rulesets;

import com.skypro.teamwork3.dto.DynamicRuleDTO;
import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.jpa.repository.DynamicRecommendationRepository;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.services.RuleValidationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DynamicRuleSet implements RecommendationRuleSet {
    private final RuleValidationService ruleValidationService;
    private final DynamicRecommendationRepository dynamicRecommendationRepository;
    private final Logger logger = LoggerFactory.getLogger(DynamicRuleSet.class);

    public DynamicRuleSet(RuleValidationService ruleValidationService, DynamicRecommendationRepository dynamicRecommendationRepository) {
        this.ruleValidationService = ruleValidationService;
        this.dynamicRecommendationRepository = dynamicRecommendationRepository;
    }

    @Transactional
    @Override
    public List<RecommendationDTO> getRecommendation(String userId) {
        logger.info("Processing getRec in DynamicRuleSet");
        return dynamicRecommendationRepository.findAll().stream()
                .map(recommendation -> processRecommendation(recommendation, userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<RecommendationDTO> processRecommendation(Recommendation recommendation, String userId) {
        logger.info("Processing each recommendation");
        Boolean isValid = recommendation.getDynamicRules().stream()
                .map(dynamicRule -> ruleValidationService.evaluateForQuery(dynamicRule, userId))
                .reduce(true, (a, b) -> a && b);

        if (isValid) {
            return Optional.of(toDTO(recommendation));
        } else {

            return Optional.empty();
        }
    }

    private RecommendationDTO toDTO(Recommendation recommendation) {
        return new RecommendationDTO(
                recommendation.getRecommendationId(),
                recommendation.getName(),
                recommendation.getText(),
                recommendation.getDynamicRules().stream()
                        .map(rule -> new DynamicRuleDTO(rule.getQuery(), rule.getArguments(), rule.isNegate()))
                        .toList()
        );
    }
}
