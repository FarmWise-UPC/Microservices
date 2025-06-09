package com.agrotech.post.post.application.internal.outboundservices.acl;

import com.agrotech.post.post.infrastructure.outbound.user.dtos.AdvisorView;
import com.agrotech.post.post.infrastructure.outbound.user.rest.AdvisorRestClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdvisorExternalService {
    private final AdvisorRestClient advisorRestClient;

    public AdvisorExternalService(AdvisorRestClient advisorRestClient) {
        this.advisorRestClient = advisorRestClient;
    }

    public Optional<AdvisorView> fetchAdvisorById(Long advisorId) {
        return advisorRestClient.getAdvisorById(advisorId);
    }
}
