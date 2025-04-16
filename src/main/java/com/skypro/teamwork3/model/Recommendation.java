package com.skypro.teamwork3.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recommendation")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "recommendation_id", nullable = false)
    private String recommendationId;

    @Column(name = "recommendation_name", nullable = false)
    private String name;

    @Column(name = "recommendation_text", nullable = false)
    private String text;

    @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DynamicRule> dynamicRules;


    public Recommendation(String recommendationId, String name, String text, List<DynamicRule> dynamicRules) {
        this.recommendationId = recommendationId;
        this.name = name;
        this.text = text;
        this.dynamicRules = dynamicRules;
    }

    public Recommendation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecommendationId() {
        return recommendationId;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public List<DynamicRule> getDynamicRules() {
        return dynamicRules;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDynamicRules(List<DynamicRule> dynamicRules) {
        this.dynamicRules = dynamicRules;
        if (dynamicRules != null) {
            for (DynamicRule dynamicRule : dynamicRules) {
                dynamicRule.setRecommendation(this);
            }
        }
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", recommendationId='" + recommendationId + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", dynamicRules=" + dynamicRules +
                '}';
    }
}