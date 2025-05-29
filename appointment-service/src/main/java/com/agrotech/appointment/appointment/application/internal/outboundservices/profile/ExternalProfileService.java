// ExternalProfileService.java
package com.agrotech.appointment.appointment.application.internal.outboundservices.profile;

import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.AdvisorView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.FarmerView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.rest.ProfileRestClient;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.ProfileView;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ExternalProfileService {
    private final ProfileRestClient profileRestClient;

    public ExternalProfileService(ProfileRestClient profileRestClient) {
        this.profileRestClient = profileRestClient;
    }

    public Optional<AdvisorView> fetchAdvisorById(Long advisorId) {
        return profileRestClient.getAdvisorById(advisorId);
    }

    public Optional<FarmerView> fetchFarmerById(Long farmerId) {
        return profileRestClient.getFarmerById(farmerId);
    }


    public void updateRating(Long advisorId, BigDecimal rating) {
        profileRestClient.updateRating(advisorId, rating);
    }

    public Optional<ProfileView> fetchProfileByUserId(Long userId) {
        return profileRestClient.getProfileByUserId(userId);
    }
}
