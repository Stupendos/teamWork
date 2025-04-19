package com.skypro.teamwork3.dto;

import com.skypro.teamwork3.model.DynamicRuleStatistics;

import java.util.ArrayList;
import java.util.List;

public class StatisticsListDTO {
    private final List<StatisticsListElement> stats;

    public StatisticsListDTO(List<DynamicRuleStatistics> statisticsList) {
        List<StatisticsListElement> stats = new ArrayList<>();
        statisticsList.stream().forEach(stat -> {
            StatisticsListElement element = new StatisticsListElement(stat.getRule().getId(), stat.getTriggerCount());
            stats.add(element);
        });
        this.stats = stats;
    }

    public List<StatisticsListElement> getStats() {
        return stats;
    }
}
