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
    private final DynamicRuleSet dynamicRuleSet;

    public RecommendationController(RecommendationService recommendationService, DynamicRuleSet dynamicRuleSet) {
        this.recommendationService = recommendationService;
        this.dynamicRuleSet = dynamicRuleSet;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecommendationDTO>> getRecommendation(@PathVariable String userId) {
        List<RecommendationDTO> recommendation = recommendationService.getRecommendations(userId);
        if (recommendation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(recommendation);
    }

    @PostMapping
    public ResponseEntity<Recommendation> createRecommendation(@RequestBody RecommendationDTO recommendationDTO) {
        try {
            Recommendation savedRecommendation = recommendationService.createRecommendation(recommendationDTO);
            return new ResponseEntity<>(savedRecommendation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Recommendation>> getAllRecommendations() {
        List<Recommendation> allRec = recommendationService.getAllRecommendations();
        if(allRec.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(allRec);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long id) {
        try {
            recommendationService.deleteRecommendation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {}
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
