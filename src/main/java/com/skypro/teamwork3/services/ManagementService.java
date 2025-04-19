package com.skypro.teamwork3.services;

import com.skypro.teamwork3.dto.ServiceInfo;
import com.skypro.teamwork3.jdbc.repository.RecommendationRepository;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

@Service
public class ManagementService {
    private final RecommendationRepository recommendationRepository;
    private final BuildProperties buildProperties;

    public ManagementService(RecommendationRepository recommendationRepository, BuildProperties buildProperties) {
        this.recommendationRepository = recommendationRepository;
        this.buildProperties = buildProperties;
    }

    public void clearCaches() {
        recommendationRepository.clearCaches();
    }

    public ServiceInfo getServiceInfo() {
        return new ServiceInfo(buildProperties.getName(), buildProperties.getVersion());
    }
}
