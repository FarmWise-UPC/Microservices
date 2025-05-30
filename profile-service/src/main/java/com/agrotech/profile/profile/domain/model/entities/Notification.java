package com.agrotech.profile.profile.domain.model.entities;

import com.agrotech.iamservice.iam.domain.model.aggregates.User;
import com.agrotech.profile.profile.domain.model.commands.CreateNotificationCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Title is required")
    private String title;
    @NotNull(message = "Message is required")
    private String message;
    @NotNull(message = "Date is required")
    private Date sendAt;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    public Notification() {}

    public Notification(CreateNotificationCommand command) {
        this.title = command.title();
        this.message = command.message();
        this.sendAt = command.sendAt();
        this.userId = command.userId();
    }

}
