package com.agrotech.appointment.appointment.domain.exceptions;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String status) {
        super("Invalid status: " + status + ". Status should be either 'PENDING', 'ONGOING', 'COMPLETED''");
    }
}
