package com.skypro.teamwork3.dto;

import com.skypro.teamwork3.model.DynamicRule;

import java.util.List;
import java.util.UUID;

public class RecommendationDTO {
    private UUID id;
    private String name;
    private String description;
    private List<DynamicRule> dynamicRules; ;

    public RecommendationDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public RecommendationDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DynamicRule> getDynamicRules() {
        return dynamicRules;
    }

    public void setDynamicRules(List<DynamicRule> dynamicRules) {
        this.dynamicRules = dynamicRules;
    }
}
