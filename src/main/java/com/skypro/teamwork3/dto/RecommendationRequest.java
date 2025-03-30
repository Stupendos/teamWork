package com.skypro.teamwork3.dto;

import com.skypro.teamwork3.model.DynamicRule;

import java.util.List;

public class RecommendationRequest {
    private String userId;
    private List<DynamicRule> dynamicRules;
    private String productName;
    private String productId;
    private String productText;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<DynamicRule> getDynamicRules() {
        return dynamicRules;
    }

    public void setDynamicRules(List<DynamicRule> dynamicRules) {
        this.dynamicRules = dynamicRules;
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
}
