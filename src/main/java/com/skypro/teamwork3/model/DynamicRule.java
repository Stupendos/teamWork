package com.skypro.teamwork3.model;

import jakarta.persistence.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Entity
@Table(name = "dynamic_rules")
public class DynamicRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String query;

    @ElementCollection
    @CollectionTable(name = "rule_arguments", joinColumns = @JoinColumn(name = "rule_id"))
    @Column(name = "argument")
    private List<String> arguments;

    @Column(name = "negate", nullable = false)
    boolean negate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id", nullable = false)
    private RecommendationByRules recommendationByRules;

    public RecommendationByRules getRecommendationByRules() {
        return recommendationByRules;
    }

    public void setRecommendation(RecommendationByRules recommendationByRules) {
        this.recommendationByRules = recommendationByRules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
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
}
