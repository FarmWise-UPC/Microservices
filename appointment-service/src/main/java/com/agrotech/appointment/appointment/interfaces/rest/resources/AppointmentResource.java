package com.agrotech.appointment.appointment.interfaces.rest.resources;

public record AppointmentResource(Long id,
                                  Long farmerId,
                                  Long availableDateId,
                                  String message,
                                  String status,
                                  String meetingUrl) {
}
