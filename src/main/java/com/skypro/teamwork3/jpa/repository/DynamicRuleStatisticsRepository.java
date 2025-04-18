package com.skypro.teamwork3.jpa.repository;

import com.skypro.teamwork3.model.DynamicRuleStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicRuleStatisticsRepository extends JpaRepository<DynamicRuleStatistics, Long> {
}
