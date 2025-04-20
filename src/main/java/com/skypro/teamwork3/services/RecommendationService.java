package com.skypro.teamwork3.services;

import com.skypro.teamwork3.dto.*;
import com.skypro.teamwork3.exceptions.NoRecommendationFound;
import com.skypro.teamwork3.exceptions.UsernameDontExistException;
import com.skypro.teamwork3.jdbc.repository.RecommendationRepository;
import com.skypro.teamwork3.jpa.repository.DynamicRecommendationRepository;
import com.skypro.teamwork3.jpa.repository.DynamicRuleRepository;
import com.skypro.teamwork3.model.DynamicRule;
import com.skypro.teamwork3.model.DynamicRuleStatistics;
import com.skypro.teamwork3.model.Recommendation;
import com.skypro.teamwork3.model.User;
import com.skypro.teamwork3.rulesets.RecommendationRuleSet;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> ruleSets;
    private final DynamicRecommendationRepository dynamicRecommendationRepository;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final RecommendationRepository defaultRecommendationRepository;
    private final RuleStatisticsService statisticsService;

    private final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    public RecommendationService(List<RecommendationRuleSet> ruleSets,
                                 DynamicRecommendationRepository dynamicRecommendationRepository,
                                 DynamicRuleRepository dynamicRuleRepository,
                                 RecommendationRepository defaultRecommendationRepository, RuleStatisticsService statisticsService) {
        this.ruleSets = ruleSets;
        this.dynamicRecommendationRepository = dynamicRecommendationRepository;
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.defaultRecommendationRepository = defaultRecommendationRepository;
        this.statisticsService = statisticsService;
    }

    public List<RecommendationDTO> getRecommendations(String userId) {
        logger.info("Processing getRecommendations...");
        List<RecommendationDTO> recommendationDTOs = new ArrayList<>();
        for (RecommendationRuleSet ruleSet : ruleSets) {
            logger.trace("Processing ruleset iteration");
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
        List<DynamicRuleStatistics> statisticsList = new ArrayList<>();
        for (DynamicRuleDTO dynamicRuleDTO : recommendationDTO.getDynamicRules()) {
            DynamicRule dynamicRule = new DynamicRule();
            dynamicRule.setQuery(dynamicRuleDTO.getQuery());
            dynamicRule.setArguments(dynamicRuleDTO.getArguments());
            dynamicRule.setNegate(dynamicRuleDTO.isNegate());

            dynamicRule.setRecommendation(recommendation);

            DynamicRuleStatistics statistics = new DynamicRuleStatistics(dynamicRule);

            dynamicRules.add(dynamicRule);
            statisticsList.add(statistics);
        }
        dynamicRuleRepository.saveAll(dynamicRules);
        statisticsService.createAll(statisticsList);
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

    public List<RecommendationDTO> getRecommendationsByUsername(String username) {
        String userId = getUserIdByUsername(username);
        List<RecommendationDTO> recList = getRecommendations(userId);

        if (recList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Search for recommendations for user " + username + " failed.");
        }
        return recList;
    }

    private String getUserIdByUsername(String username) throws UsernameDontExistException {
        try {
            logger.info("Fetching userId by username: {} from the database.", username);
            String userId = defaultRecommendationRepository.getIdByUsername(username);
            return userId;
        } catch (Exception e) {
            logger.error("User with username: {} not found ", username);
            throw new UsernameDontExistException("Id search by username failed for: " + username);
        }
    }

    public String findFullNameByUsername(String username) throws UsernameDontExistException {
        logger.info("Searching for full name...");
        try {
            User user = defaultRecommendationRepository.getAllByUsername(username);
            return (user.getFirstName() + " " + user.getLastName());
        } catch (Exception e) {
            logger.error(e.getClass().toString(), e.getMessage());
            throw new UsernameDontExistException("User search by username failed: " + username);
        }
    }
}
