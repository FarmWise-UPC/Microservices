package com.agrotech.profile.profile.infrastructure.outbound.user.rest;

import com.agrotech.profile.profile.infrastructure.outbound.user.dtos.UserView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class IamRestClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public IamRestClient(RestTemplate restTemplate,
                         @Value("${iam.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<UserView> getUserById(Long userId) {
        try {
            var user = restTemplate.getForObject(baseUrl + "/api/v1/users/" + userId, UserView.class);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
