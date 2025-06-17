package com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos;

import java.util.Date;

public record NotificationView(
        Long id,
        Long userId,
        String title,
        String message,
        Date sendAt
) {}