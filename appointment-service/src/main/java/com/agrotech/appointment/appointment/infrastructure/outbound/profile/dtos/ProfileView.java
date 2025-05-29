package com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos;

import java.time.LocalDate;

public record ProfileView(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        String city,
        String country,
        LocalDate birthDate,
        String description,
        String photo,
        String occupation,
        Integer experience
) {
}
