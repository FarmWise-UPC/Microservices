package com.agrotech.profile.profile.infrastructure.outbound.user.rest;

import com.agrotech.profile.profile.infrastructure.outbound.user.dtos.UserView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

import java.util.Optional;

@Component
public class IamRestClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public IamRestClient(RestTemplate restTemplate,
                         @Value("${iam.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<UserView> getUserById(Long userId, String token) {
        String cleanedToken = token.replace("Bearer ", "");
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(cleanedToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<UserView> response = restTemplate.exchange(
                    baseUrl + "/api/v1/users/" + userId,
                    HttpMethod.GET,
                    entity,
                    UserView.class
            );
            System.out.println("Response: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
