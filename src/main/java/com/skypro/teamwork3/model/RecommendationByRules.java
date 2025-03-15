package com.skypro.teamwork3.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recommendation_by_ryles")
public class RecommendationByRules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_text", nullable = false)
    private String productText;

    @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DynamicRule> rules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<DynamicRule> getRules() {
        return rules;
    }

    public void setRules(List<DynamicRule> rules) {
        this.rules = rules;
    }
}
