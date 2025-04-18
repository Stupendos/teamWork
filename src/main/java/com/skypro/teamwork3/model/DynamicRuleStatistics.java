package com.skypro.teamwork3.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "dynamic_rule_statistics")
public class DynamicRuleStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id")
    private DynamicRule rule;
    @Column(name = "trigger_count")
    private int triggerCount = 0;

    public DynamicRuleStatistics() {
    }

    @JsonCreator
    public DynamicRuleStatistics(DynamicRule rule) {
        this.rule = rule;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DynamicRule getRule() {
        return rule;
    }

    public void setRule(DynamicRule rule) {
        this.rule = rule;
    }

    public int getTriggerCount() {
        return triggerCount;
    }

    public void setTriggerCount(int triggerCount) {
        this.triggerCount = triggerCount;
    }
}
