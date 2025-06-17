package com.agrotech.appointment.appointment.domain.services;

import com.agrotech.appointment.appointment.domain.model.commands.CreateReviewCommand;
import com.agrotech.appointment.appointment.domain.model.commands.DeleteReviewCommand;
import com.agrotech.appointment.appointment.domain.model.commands.UpdateReviewCommand;
import com.agrotech.appointment.appointment.domain.model.entities.Review;

import java.util.Optional;

public interface ReviewCommandService {
    Long handle(CreateReviewCommand command, String token);
    Optional<Review> handle(UpdateReviewCommand command, String token);
    void handle(DeleteReviewCommand command);
}
