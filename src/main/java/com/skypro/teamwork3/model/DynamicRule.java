package com.skypro.teamwork3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QueryType query;

    private List<String> arguments;

    @Column(name = "negate", nullable = false)
    boolean negate;

    @OneToOne(mappedBy = "rule", orphanRemoval = true)
    @JsonIgnore
    private DynamicRuleStatistics ruleStatistics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id", nullable = false)
    @JsonBackReference
    @JsonIgnore
    private Recommendation recommendation;

    @JsonCreator
    public DynamicRule(QueryType query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public DynamicRule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QueryType getQuery() {
        return query;
    }

    public void setQuery(QueryType query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    public DynamicRuleStatistics getRuleStatistics() {
        return ruleStatistics;
    }

    public void setRuleStatistics(DynamicRuleStatistics ruleStatistics) {
        this.ruleStatistics = ruleStatistics;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    @Override
    public String toString() {
        return "DynamicRule{" +
                "id=" + id +
                ", query=" + query +
                ", arguments=" + arguments +
                ", negate=" + negate +
                '}';
    }
}
