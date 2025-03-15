package com.skypro.teamwork3.services;

import com.skypro.teamwork3.model.DynamicRule;
import com.skypro.teamwork3.jpa.repository.DynamicRuleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicRuleService {
    private final DynamicRuleRepository dynamicRuleRepository;

    public DynamicRuleService(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    public DynamicRule addRule(DynamicRule dynamicRule) {
        return dynamicRuleRepository.save(dynamicRule);
    }

    public void deleteRule(Long id) {
        if (!dynamicRuleRepository.existsById(id)) {
            throw new EntityNotFoundException("Правило с ID " + id + " не найдено.");
        }
        dynamicRuleRepository.deleteById(id);
    }

    public List<DynamicRule> getAllRules() {
        return dynamicRuleRepository.findAll();
    }

}
