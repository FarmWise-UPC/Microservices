package com.agrotech.management.management.infrastructure.outbound.profile.rest;

import com.agrotech.management.management.infrastructure.outbound.profile.dtos.FarmerView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    public Optional<FarmerView> getFarmerById(Long farmerId, String token) {
        String cleanedToken = token.replace("Bearer ", "");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(cleanedToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<FarmerView> response = restTemplate.exchange(
                    baseUrl + "/api/v1/farmers/" + farmerId,
                    HttpMethod.GET,
                    entity,
                    FarmerView.class
            );

            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}