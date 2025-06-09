package com.agrotech.post.post.infrastructure.outbound.user.rest;

import com.agrotech.post.post.infrastructure.outbound.user.dtos.AdvisorView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
public class AdvisorRestClient  {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public  AdvisorRestClient(RestTemplate restTemplate,
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
}


