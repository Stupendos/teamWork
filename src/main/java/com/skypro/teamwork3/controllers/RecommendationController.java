package com.skypro.teamwork3.controllers;

import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.rulesets.DynamicRuleSet;
import com.skypro.teamwork3.services.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService, DynamicRuleSet dynamicRuleSet) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecommendationDTO>> getRecommendation(@PathVariable String userId) {
        List<RecommendationDTO> recommendation = recommendationService.getRecommendations(userId);
        if (recommendation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(recommendation);
    }
}
