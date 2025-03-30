package com.skypro.teamwork3.jpa.repository;

import com.skypro.teamwork3.model.DynamicRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long> {
    List<DynamicRule> findAll();
}
