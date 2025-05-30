package com.agrotech.management.management.interfaces.rest.resources;

public record EnclosureResource(
        Long id,
        String name,
        Integer capacity,
        String type,
        Long farmerId
) {}
