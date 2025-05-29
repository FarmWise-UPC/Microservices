package com.agrotech.appointment.appointment.domain.model.commands;

public record CreateAppointmentCommand(Long availableDateId,
                                       Long farmerId,
                                       String message) {
}
