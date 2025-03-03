package com.skypro.teamwork3.controllers;

import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.services.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getRecommendation(@PathVariable String userId) {
        List<Recommendation> recommendations = recommendationService.getRecommendations(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("recommendations", recommendations);
        return ResponseEntity.ok(response);
    }
}
