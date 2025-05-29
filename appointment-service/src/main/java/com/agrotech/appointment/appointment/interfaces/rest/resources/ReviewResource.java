package com.agrotech.appointment.appointment.interfaces.rest.resources;

public record ReviewResource(Long id,
                             Long advisorId,
                             Long farmerId,
                             String comment,
                             Integer rating) {
}
