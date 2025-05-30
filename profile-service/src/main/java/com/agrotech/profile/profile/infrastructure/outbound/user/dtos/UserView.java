package com.agrotech.profile.profile.infrastructure.outbound.user.dtos;

public record UserView (
        Long id,
        String username,
        String[] roles
) {}
