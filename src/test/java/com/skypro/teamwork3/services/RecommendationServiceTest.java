package com.skypro.teamwork3.services;

import com.skypro.teamwork3.dto.RecommendationDTO;
import com.skypro.teamwork3.jpa.repository.DynamicRecommendationRepository;
import com.skypro.teamwork3.jpa.repository.DynamicRuleRepository;
import com.skypro.teamwork3.model.Recommendation;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("postgres")
@SpringBootTest
@Transactional
class RecommendationServiceTest {
    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private DynamicRecommendationRepository dynamicRecommendationRepository;

    @Autowired
    private DynamicRuleRepository dynamicRuleRepository;

    @BeforeEach
    void setUp() {
        dynamicRuleRepository.deleteAll();
        dynamicRecommendationRepository.deleteAll();
    }

    @Transactional
    @Test
    void createRecommendation() {
        RecommendationDTO recommendationDTO = new RecommendationDTO(
                "10",
                "Test recommendation",
                "Text recommendation",
                new ArrayList<>()
        );

        Recommendation recommendation = recommendationService.createRecommendation(recommendationDTO);

        Assertions.assertNotNull(recommendation);
        Assertions.assertEquals(recommendationDTO.getName(), recommendation.getName());
    }
    @Transactional
    @Test
    void getAllRecommendations() {
        RecommendationDTO recommendationDTO = new RecommendationDTO(
                "4",
                "Test recommendation",
                "Text recommendation",
                new ArrayList<>()
        );
        RecommendationDTO recommendationDTO2 = new RecommendationDTO(
                "2",
                "Test recommendation2",
                "Text recommendation2",
                new ArrayList<>()
        );
        recommendationService.createRecommendation(recommendationDTO);
        recommendationService.createRecommendation(recommendationDTO2);

        List<Recommendation> recommendations = recommendationService.getAllRecommendations();

        Assertions.assertEquals(2, recommendations.size());
    }
    @Transactional
    @Test
    void deleteRecommendation() {
        RecommendationDTO recommendationDTO = new RecommendationDTO("3",
                "Test recommendation",
                "Text recommendation",
                new ArrayList<>());
        Recommendation recommendation = recommendationService.createRecommendation(recommendationDTO);

        recommendationService.deleteRecommendation(recommendation.getId());

        Optional<Recommendation> deletedRecommendation = dynamicRecommendationRepository.findById(recommendation.getId());
        Assertions.assertFalse(deletedRecommendation.isPresent());
    }
}