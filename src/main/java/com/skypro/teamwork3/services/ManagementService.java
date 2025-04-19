package com.skypro.teamwork3.services;

import com.skypro.teamwork3.dto.ServiceInfo;
import com.skypro.teamwork3.jdbc.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
    private final RecommendationRepository recommendationRepository;

    public ManagementService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public void clearCaches() {
        recommendationRepository.clearCaches();
    }

    public ServiceInfo getServiceInfo() {
        return new ServiceInfo("NAME", "VERSION");
    }
}
