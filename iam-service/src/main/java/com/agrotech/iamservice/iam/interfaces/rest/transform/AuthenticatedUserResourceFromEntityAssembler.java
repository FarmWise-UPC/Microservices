package com.agrotech.iamservice.iam.interfaces.rest.transform;


import com.agrotech.iamservice.iam.domain.model.aggregates.User;
import com.agrotech.iamservice.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }
}