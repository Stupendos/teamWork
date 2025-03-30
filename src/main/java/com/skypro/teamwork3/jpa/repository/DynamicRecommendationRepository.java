package com.skypro.teamwork3.jpa.repository;

import com.skypro.teamwork3.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DynamicRecommendationRepository extends JpaRepository<Recommendation, UUID> {

}
