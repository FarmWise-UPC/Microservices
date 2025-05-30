package com.agrotech.management.management.interfaces.rest.resources;

public record UpdateAnimalResource(
        String name,
        Integer age,
        String species,
        String breed,
        Boolean gender,
        Float weight,
        String health
) {}
