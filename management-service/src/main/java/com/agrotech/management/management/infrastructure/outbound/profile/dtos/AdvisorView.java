package com.agrotech.management.management.infrastructure.outbound.profile.dtos;

import java.math.BigDecimal;

public record AdvisorView(
        Long id,
        Long userId,
        BigDecimal rating
) {}
