package com.agrotech.post.post.infrastructure.outbound.user.rest;

import com.agrotech.post.post.infrastructure.outbound.user.dtos.AdvisorView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class AdvisorRestClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AdvisorRestClient(RestTemplate restTemplate,
                             @Value("${profiles.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<AdvisorView> getAdvisorById(Long advisorId, String token) {
        String cleanedToken = token.replace("Bearer ", "");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(cleanedToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<AdvisorView> response = restTemplate.exchange(
                    baseUrl + "/api/v1/advisors/" + advisorId,
                    HttpMethod.GET,
                    entity,
                    AdvisorView.class
            );

            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}