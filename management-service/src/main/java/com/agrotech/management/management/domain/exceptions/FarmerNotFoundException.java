package com.agrotech.management.management.domain.exceptions;

public class FarmerNotFoundException extends RuntimeException {
    public FarmerNotFoundException(Long id) {
        super("Farmer with ID " + id + " not found.");
    }
}
