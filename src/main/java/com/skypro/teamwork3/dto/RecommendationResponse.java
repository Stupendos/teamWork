package com.skypro.teamwork3.dto;

import com.skypro.teamwork3.model.DynamicRule;

import java.util.List;

public class RecommendationResponse {
    private String id;
    private String productName;
    private String productId;
    private String productText;
    private List<DynamicRule> dynamicRules;

    public RecommendationResponse(String id, String productName, String productId, String productText, List<DynamicRule> dynamicRules) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.dynamicRules = dynamicRules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<DynamicRule> getDynamicRules() {
        return dynamicRules;
    }

    public void setDynamicRules(List<DynamicRule> dynamicRules) {
        this.dynamicRules = dynamicRules;
    }
}
