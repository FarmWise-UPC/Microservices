package com.agrotech.appointment.appointment.domain.model.queries;

import java.time.LocalDate;

public record GetAvailableDateByAdvisorIdAndDate(
        Long advisorId,
        LocalDate scheduledDate,
        String startTime,
        String endTime
) {
}
