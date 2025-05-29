package com.agrotech.appointment.appointment.domain.model.entities;

import com.agrotech.appointment.appointment.domain.model.commands.CreateReviewCommand;
import com.agrotech.appointment.appointment.domain.model.commands.UpdateReviewCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "advisor_id")
    private Long advisorId;

    @NotNull
    @Column(name = "farmer_id")
    private Long farmerId;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Min(value = 0, message = "Rating must be between 0 and 5")
    @Max(value = 5, message = "Rating must be between 0 and 5")
    @NotNull(message = "Rating is required")
    private Integer rating;

    public Review() {
    }

    public Review(CreateReviewCommand command, Long advisorId, Long farmerId) {
        this.comment = command.comment();
        this.rating = command.rating();
        this.advisorId = advisorId;
        this.farmerId = farmerId;
    }

    public Review update(UpdateReviewCommand command) {
        this.comment = command.comment();
        this.rating = command.rating();
        return this;
    }
}