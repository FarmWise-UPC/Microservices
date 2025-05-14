package com.agrotech.profile.profile.domain.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(Long id) {
        super("Profile with id " + id + " not found");
    }
}
