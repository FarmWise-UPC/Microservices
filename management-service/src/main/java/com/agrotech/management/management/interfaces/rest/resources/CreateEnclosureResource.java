package com.agrotech.management.management.interfaces.rest.resources;


public record CreateEnclosureResource(
        String name,
        Integer capacity,
        String type,
        Long farmerId
) {}
