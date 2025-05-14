package com.agrotech.iamservice.iam.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " not found");
    }
}
