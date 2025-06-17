// ExternalProfileService.java
package com.agrotech.appointment.appointment.application.internal.outboundservices.profile;

import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.AdvisorView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.FarmerView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.NotificationView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.rest.ProfileRestClient;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.ProfileView;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class ExternalProfileService {
    private final ProfileRestClient profileRestClient;

    public ExternalProfileService(ProfileRestClient profileRestClient) {
        this.profileRestClient = profileRestClient;
    }

    public Optional<AdvisorView> fetchAdvisorById(Long advisorId, String token) {
        return profileRestClient.getAdvisorById(advisorId, token);
    }

    public Optional<FarmerView> fetchFarmerById(Long farmerId, String token) {
        return profileRestClient.getFarmerById(farmerId, token);
    }


    public void updateRating(Long advisorId, BigDecimal rating, String token) {
        profileRestClient.updateRating(advisorId, rating, token);
    }

    public Optional<ProfileView> fetchProfileByUserId(Long userId, String token) {
        return profileRestClient.getProfileByUserId(userId, token);
    }

    public void createNotification(Long userId, String title, String message, Date sendAt, String token) {
        profileRestClient.sendNotification(userId, title, message, sendAt, token);
    }
}
