package com.agrotech.profile.profile.domain.model.entities;

import com.agrotech.profile.profile.domain.model.commands.CreateAdvisorCommand;
import com.agrotech.profile.profile.domain.model.commands.UpdateAdvisorCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class Advisor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull(message = "Rating is required")
    @Column(precision = 3, scale = 2)
    BigDecimal rating;

    public Advisor() {
        this.rating = BigDecimal.valueOf(0.00);
    }

    public Advisor(Long userId) {
        this.rating = BigDecimal.valueOf(0.00);
        this.userId = userId;
    }

    public Advisor(CreateAdvisorCommand createAdvisorCommand) {
        this.userId = createAdvisorCommand.userId();
        this.rating = BigDecimal.valueOf(0.00);
    }

    public Advisor update(UpdateAdvisorCommand command) {
        this.rating = command.rating();
        return this;
    }

}
