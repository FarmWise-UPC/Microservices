package com.agrotech.management.management.infrastructure.outbound.profile.rest;

import com.agrotech.management.management.infrastructure.outbound.profile.dtos.AdvisorView;
import com.agrotech.management.management.infrastructure.outbound.profile.dtos.FarmerView;
import com.agrotech.management.management.infrastructure.outbound.profile.dtos.ProfileView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ProfileRestClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ProfileRestClient(RestTemplate restTemplate,
                              @Value("${profiles.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<AdvisorView> getAdvisorById(Long advisorId) {
        try {
            var advisor = restTemplate.getForObject(baseUrl + "/api/v1/advisors/" + advisorId, AdvisorView.class);
            return Optional.ofNullable(advisor);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<FarmerView> getFarmerById(Long farmerId) {
        try {
            var farmer = restTemplate.getForObject(baseUrl + "/api/v1/farmers/" + farmerId, FarmerView.class);
            return Optional.ofNullable(farmer);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<ProfileView> getProfileByUserId(Long userId) {
        try {
            var profile = restTemplate.getForObject(baseUrl + "/api/v1/profiles/" + userId + "/user", ProfileView.class);
            return Optional.ofNullable(profile);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


}
