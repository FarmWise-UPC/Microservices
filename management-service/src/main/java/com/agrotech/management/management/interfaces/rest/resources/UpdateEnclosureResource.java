package com.agrotech.management.management.interfaces.rest.resources;

public record UpdateEnclosureResource(
        String name,
        Integer capacity,
        String type
) {}
