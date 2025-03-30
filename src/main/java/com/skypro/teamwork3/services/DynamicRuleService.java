package com.skypro.teamwork3.services;

import com.skypro.teamwork3.dto.DynamicRuleDTO;
import com.skypro.teamwork3.jpa.repository.DynamicRecommendationRepository;
import com.skypro.teamwork3.model.DynamicRule;
import com.skypro.teamwork3.jpa.repository.DynamicRuleRepository;
import com.skypro.teamwork3.model.Recommendation;
import jakarta.persistence.EntityNotFoundException;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class DynamicRuleService {
//    private final DynamicRuleRepository dynamicRuleRepository;
//    private final DynamicRecommendationRepository dynamicRecommendationRepository;
//
//    public DynamicRuleService(DynamicRuleRepository dynamicRuleRepository, DynamicRecommendationRepository dynamicRecommendationRepository) {
//        this.dynamicRuleRepository = dynamicRuleRepository;
//        this.dynamicRecommendationRepository = dynamicRecommendationRepository;
//    }
//
//    public DynamicRule createDynamicRule(DynamicRuleDTO dynamicRuleDTO) {
//        DynamicRule dynamicRule = new DynamicRule();
//        dynamicRule.setQuery(dynamicRuleDTO.getQuery());
//        dynamicRule.setArguments(dynamicRuleDTO.getArguments());
//        dynamicRule.setNegate(dynamicRuleDTO.isNegate());
//
////        if (dynamicRuleDTO.getRecommendationId() != null) {
////            Recommendation recommendation = dynamicRecommendationRepository.findById(dynamicRuleDTO.getRecommendationId())
////                    .orElseThrow(() -> new EntityNotFoundException("Рекомендация не найдена"));
////            dynamicRule.setRecommendation(recommendation);
////        }
//        System.out.println("Saving DynamicRule: {}" + dynamicRule.toString());
//
//        return dynamicRuleRepository.save(dynamicRule);
//    }
//
//    public void deleteRule(Long id) {
//        if (!dynamicRuleRepository.existsById(id)) {
//            throw new EntityNotFoundException("Правило с ID " + id + " не найдено.");
//        }
//        dynamicRuleRepository.deleteById(id);
//    }
//
//    public List<DynamicRule> getAllRules() {
//        return dynamicRuleRepository.findAll();
//    }
//}
