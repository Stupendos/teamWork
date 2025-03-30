package com.skypro.teamwork3.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recommendation")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "recommendation_id", nullable = false)
    private UUID recommendationId;

    @Column(name = "recommendation_name", nullable = false)
    private String name;

    @Column(name = "recommendation_text", nullable = false)
    private String text;

    @OneToMany(mappedBy = "recommendation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DynamicRule> dynamicRules = new ArrayList<>();

    public Recommendation(UUID recommendationId, String name, String text) {
        this.recommendationId = recommendationId;
        this.name = name;
        this.text = text;
    }

    public Recommendation() {

    }

    public UUID getRecommendationId() {
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

    public void setRecommendationId(UUID recommendationId) {
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