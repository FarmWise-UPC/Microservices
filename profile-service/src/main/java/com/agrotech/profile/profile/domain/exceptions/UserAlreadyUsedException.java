package com.agrotech.profile.profile.domain.exceptions;

public class UserAlreadyUsedException extends RuntimeException {
    public UserAlreadyUsedException(Long userId) {
        super("User with ID " + userId + " is already being used.");
    }
}
