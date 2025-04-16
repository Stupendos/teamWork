package com.skypro.teamwork3.dto;

import java.util.List;

public class RecommendationDTO {
    private String recommendationId;
    private String name;
    private String description;
    private List<DynamicRuleDTO> dynamicRules;

    public RecommendationDTO(String recommendationId, String name, String text, List<DynamicRuleDTO> dynamicRules) {
        this.recommendationId = recommendationId;
        this.name = name;
        this.description = text;
        this.dynamicRules = dynamicRules;
    }

    public RecommendationDTO() {
    }

    public String getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
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

    public List<DynamicRuleDTO> getDynamicRules() {
        return dynamicRules;
    }

    public void setDynamicRules(List<DynamicRuleDTO> dynamicRules) {
        this.dynamicRules = dynamicRules;
    }

    @Override
    public String toString() {
        return "RecommendationDTO{" +
                "recommendationId='" + recommendationId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dynamicRules=" + dynamicRules +
                '}';
    }
}
