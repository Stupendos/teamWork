package com.skypro.teamwork3.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skypro.teamwork3.dto.StatisticsListDTO;
import com.skypro.teamwork3.jpa.repository.DynamicRuleStatisticsRepository;
import com.skypro.teamwork3.model.DynamicRuleStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleStatisticsService {
    private final DynamicRuleStatisticsRepository repository;
    private final Logger logger = LoggerFactory.getLogger(RuleStatisticsService.class);

    public RuleStatisticsService(DynamicRuleStatisticsRepository repository) {
        this.repository = repository;
    }

    public void createAll(List<DynamicRuleStatistics> statisticsList) {
        repository.saveAll(statisticsList);
    }

    public void increase(Long ruleId) {
        DynamicRuleStatistics statistics = repository.findByRuleId(ruleId);
        if (statistics != null) {
            statistics.setTriggerCount((statistics.getTriggerCount() + 1));
            repository.save(statistics);
        }
    }

    public StatisticsListDTO getAll() {
        logger.info("Processing getAll");
        List<DynamicRuleStatistics> list = repository.findAll();
        return new StatisticsListDTO(list);
    }
}
