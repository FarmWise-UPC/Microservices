package com.agrotech.post.application.internal.outboundservices.acl;

import com.agrotech.post.infrastructure.profile.AdvisorRestClient;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdvisorExternalService {
    private final AdvisorRestClient advisorRestClient;

    public AdvisorExternalService(AdvisorRestClient advisorRestClient) {
        this.advisorRestClient = advisorRestClient;
    }

    public Optional<Boolean> checkAdvisorExists(Long advisorId) {
        return advisorRestClient.checkAdvisorExists(advisorId);
    }
}
