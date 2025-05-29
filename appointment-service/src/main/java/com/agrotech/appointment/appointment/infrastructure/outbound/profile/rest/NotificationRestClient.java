package com.agrotech.appointment.appointment.infrastructure.outbound.profile.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationRestClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public NotificationRestClient(RestTemplate restTemplate,
                                  @Value("${profiles.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public Long sendNotification(Long userId, String title, String message, Date sendAt) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("title", title);
        payload.put("message", message);
        payload.put("sendAt", sendAt);

        return restTemplate.postForObject(baseUrl + "/api/v1/notifications", payload, Long.class);
    }
}
