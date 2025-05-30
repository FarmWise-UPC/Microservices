package com.agrotech.profile.profile.domain.model.entities;

import com.agrotech.iamservice.iam.domain.model.aggregates.User;
import com.agrotech.profile.profile.domain.model.commands.CreateFarmerCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Getter
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    public Farmer() {
    }

    public Farmer(CreateFarmerCommand command) {
        this.userId = command.userId();
    }

}
