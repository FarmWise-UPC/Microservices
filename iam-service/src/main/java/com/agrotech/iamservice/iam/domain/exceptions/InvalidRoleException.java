package com.agrotech.iamservice.iam.domain.exceptions;

import com.agrotech.iamservice.iam.domain.model.valueobjects.Roles;

import java.util.List;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String role) {
        super("Invalid role: " + role + ". Please use one of the list: " + List.of(Roles.values()));
    }
}
