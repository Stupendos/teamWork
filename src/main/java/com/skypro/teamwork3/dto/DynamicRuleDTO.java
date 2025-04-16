package com.skypro.teamwork3.dto;

import com.skypro.teamwork3.model.QueryType;

import java.util.List;

public class DynamicRuleDTO {
    private QueryType query;
    private List<String> arguments;
    private boolean negate;

    public DynamicRuleDTO() {
    }

    public DynamicRuleDTO(QueryType query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
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

    @Override
    public String toString() {
        return "DynamicRuleDTO{" +
                "query=" + query +
                ", arguments=" + arguments +
                ", negate=" + negate +
                '}';
    }
}
