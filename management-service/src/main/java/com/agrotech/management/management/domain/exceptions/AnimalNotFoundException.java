package com.agrotech.management.management.domain.exceptions;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(Long animalId) {
        super("Animal with id " + animalId + " not found");
    }
}
