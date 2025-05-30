package com.agrotech.profile.profile.application.internal.outboundservices.acl;

import com.agrotech.iamservice.iam.domain.model.aggregates.User;
import com.agrotech.iamservice.iam.interfaces.acl.IamContextFacade;
import com.agrotech.profile.profile.infrastructure.outbound.user.dtos.UserView;
import com.agrotech.profile.profile.infrastructure.outbound.user.rest.IamRestClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalUserService {
    private final IamRestClient iamRestClient;

    public ExternalUserService(IamRestClient iamRestClient) {
        this.iamRestClient = iamRestClient;
    }

    public Optional<UserView> fetchUserById(Long userId) {
        return iamRestClient.getUserById(userId);
    }
}
