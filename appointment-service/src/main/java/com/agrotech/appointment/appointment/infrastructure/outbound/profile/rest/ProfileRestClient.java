package com.agrotech.appointment.appointment.infrastructure.outbound.profile.rest;

import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.AdvisorView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.FarmerView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.NotificationView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.ProfileView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Component
public class ProfileRestClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ProfileRestClient(RestTemplate restTemplate,
                             @Value("${profiles.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Optional<AdvisorView> getAdvisorById(Long advisorId, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token.replace("Bearer ", ""));
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

    public Optional<FarmerView> getFarmerById(Long farmerId, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token.replace("Bearer ", ""));
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

    public Optional<ProfileView> getProfileByUserId(Long userId, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token.replace("Bearer ", ""));
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<ProfileView> response = restTemplate.exchange(
                    baseUrl + "/api/v1/profiles/" + userId + "/user",
                    HttpMethod.GET,
                    entity,
                    ProfileView.class
            );

            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void updateRating(Long advisorId, BigDecimal rating, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.replace("Bearer ", ""));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BigDecimal> entity = new HttpEntity<>(rating, headers);

        restTemplate.exchange(
                baseUrl + "/api/v1/advisors/" + advisorId + "/rating",
                HttpMethod.PUT,
                entity,
                Void.class
        );
    }

    public Optional<NotificationView> sendNotification(Long userId, String title, String message, Date sendAt, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.replace("Bearer ", ""));
        headers.setContentType(MediaType.APPLICATION_JSON);

        NotificationView notification = new NotificationView(null, userId, title, message, sendAt);
        HttpEntity<NotificationView> entity = new HttpEntity<>(notification, headers);

        try {
            ResponseEntity<NotificationView> response = restTemplate.exchange(
                    baseUrl + "/api/v1/notifications",
                    HttpMethod.POST,
                    entity,
                    NotificationView.class
            );

            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}