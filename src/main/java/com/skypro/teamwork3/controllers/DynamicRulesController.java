package com.skypro.teamwork3.controllers;

import com.google.gson.JsonObject;
import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.dto.StatisticsListDTO;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.services.RecommendationService;
import com.skypro.teamwork3.services.RuleStatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rule")
public class DynamicRulesController {

    private final RecommendationService recommendationService;
    private final RuleStatisticsService statisticsService;

    public DynamicRulesController(RecommendationService recommendationService, RuleStatisticsService statisticsService) {
        this.recommendationService = recommendationService;
        this.statisticsService = statisticsService;
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

    @GetMapping("/stats")
    public ResponseEntity<StatisticsListDTO> getRuleStatistics() {
        return ResponseEntity.ok(statisticsService.getAll());
    }
}
