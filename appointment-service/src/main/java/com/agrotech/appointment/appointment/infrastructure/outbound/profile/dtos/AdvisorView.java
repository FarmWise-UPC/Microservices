package com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos;

import java.math.BigDecimal;

public record AdvisorView(
        Long id,
        Long userId,
        BigDecimal rating
) {}
