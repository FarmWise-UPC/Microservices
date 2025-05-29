package com.agrotech.appointment.appointment.domain.model.commands;

import java.time.LocalDate;

public record CreateAvailableDateCommand(Long advisorId,
                                         LocalDate scheduledDate,
                                         String startTime,
                                         String endTime) {
}
