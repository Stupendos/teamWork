package com.skypro.teamwork3.services;

import com.skypro.teamwork3.jdbc.repository.RecommendationRepository;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private final RecommendationRepository recommendationRepository;

    public CacheService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }


    public void clearCaches() {
        recommendationRepository.clearCaches();
    }
}
