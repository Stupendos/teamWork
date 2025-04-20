package com.skypro.teamwork3.dto;

import com.skypro.teamwork3.model.DynamicRuleStatistics;

import java.util.ArrayList;
import java.util.List;

public class StatisticsListDTO {
    private final List<StatisticsListElement> stats;

    public StatisticsListDTO(List<DynamicRuleStatistics> statisticsList) {
        this.stats = statisticsList.stream()
                .map(stat -> new StatisticsListElement(stat.getRule().getId(), stat.getTriggerCount()))
                .toList();
    }

    public List<StatisticsListElement> getStats() {
        return stats;
    }
}
