package com.agrotech.appointment.appointment.domain.exceptions;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(Long id) {
        super("Appointment with id " + id + " not found");
    }
}
