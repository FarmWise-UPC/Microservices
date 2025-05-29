package com.agrotech.appointment.appointment.interfaces.rest.resources;

public record CreateAppointmentResource(Long farmerId,
                                        Long availableDateId,
                                        String message) {
}
