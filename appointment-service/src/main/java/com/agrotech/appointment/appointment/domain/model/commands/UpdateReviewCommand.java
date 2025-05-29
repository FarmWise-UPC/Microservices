package com.agrotech.appointment.appointment.domain.model.commands;

public record UpdateReviewCommand(Long id, String comment, Integer rating) {
}
