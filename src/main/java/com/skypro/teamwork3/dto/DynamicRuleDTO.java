package com.skypro.teamwork3.dto;


import com.skypro.teamwork3.model.QueryType;

import java.util.List;

public class DynamicRuleDTO {
    private String query;
    private List<String> arguments;
    private boolean negate;
    private String recommendationId;

    public QueryType getQuery() {
        return QueryType.valueOf(query);
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

    public String getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
    }
}
