package com.agrotech.appointment.appointment.domain.model.aggregates;

import com.agrotech.appointment.appointment.domain.model.commands.CreateAppointmentCommand;
import com.agrotech.appointment.appointment.domain.model.commands.UpdateAppointmentCommand;
import com.agrotech.appointment.appointment.domain.model.entities.AvailableDate;
import com.agrotech.appointment.appointment.domain.model.valueobjects.AppointmentStatus;
import com.agrotech.appointment.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Appointment extends AuditableAbstractAggregateRoot<Appointment> {

    @NotNull(message = "Message is required")
    @Column(columnDefinition = "TEXT")
    private String message;

    @Setter
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private AppointmentStatus status;

    @NotNull
    @Column(name = "farmer_id")
    private Long farmerId;

    @NotNull
    @Column(name = "advisor_id")
    private Long advisorId;

    @OneToOne
    @JoinColumn(name = "available_date_id")
    private AvailableDate availableDate;

    private String meetingUrl;

    public Appointment() {
    }

    public Appointment(CreateAppointmentCommand command, String meetingUrl, Long farmerId, Long advisorId, AvailableDate availableDate) {
        this.message = command.message();
        this.status = AppointmentStatus.PENDING;
        this.farmerId = farmerId;
        this.advisorId = advisorId;
        this.availableDate = availableDate;
        this.meetingUrl = meetingUrl;
    }

    public Appointment update(UpdateAppointmentCommand command) {
        this.message = command.message();
        this.status = AppointmentStatus.valueOf(command.status());
        return this;
    }

    public Long getAvailableDateId() {
        return availableDate.getId();
    }

    public String getAppointmentStatus() {
        return status.toString();
    }
}