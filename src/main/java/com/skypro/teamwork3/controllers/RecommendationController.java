package com.skypro.teamwork3.controllers;

import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.rulesets.DynamicRuleSet;
import com.skypro.teamwork3.services.RecommendationService;
import com.skypro.teamwork3.services.RuleValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final RuleValidationService ruleValidationService;
    private final DynamicRuleSet dynamicRuleSet;

    public RecommendationController(RecommendationService recommendationService, RuleValidationService ruleValidationService, DynamicRuleSet dynamicRuleSet) {
        this.recommendationService = recommendationService;
        this.ruleValidationService = ruleValidationService;
        this.dynamicRuleSet = dynamicRuleSet;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecommendationDTO>> getRecommendation(@PathVariable String userId) {
        List<RecommendationDTO> recommendation = recommendationService.getRecommendations(userId);
        return ResponseEntity.ok(recommendation);
    }

    @PostMapping
    public ResponseEntity<Recommendation> createRecommendation(@RequestBody RecommendationDTO recommendationDTO) {
        Recommendation createdRecommendation = recommendationService.createRecommendation(
                recommendationDTO.getId(),
                recommendationDTO.getName(),
                recommendationDTO.getDescription(),
                recommendationDTO.getDynamicRules()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecommendation);
    }

//    @PostMapping("/{userId}")
//    public ResponseEntity<RecommendationResponse> createRecommendation(@RequestBody RecommendationRequest recommendationRequest) {
//        String userId = recommendationRequest.getUserId();
//        List<DynamicRule> dynamicRules = recommendationRequest.getDynamicRules();
//
//        boolean allRulesValid = dynamicRules.stream()
//                .allMatch(rule -> ruleValidationService.validateRule(userId, rule));
//
//        if(allRulesValid) {
//            RecommendationResponse response = new RecommendationResponse(
//                    UUID.randomUUID().toString(),
//                    recommendationRequest.getProductName(),
//                    recommendationRequest.getProductId(),
//                    recommendationRequest.getProductText(),
//                    dynamicRules
//            );
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
}
