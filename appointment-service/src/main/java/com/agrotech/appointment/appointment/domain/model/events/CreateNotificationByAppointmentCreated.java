package com.agrotech.appointment.appointment.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateNotificationByAppointmentCreated extends ApplicationEvent {
    private final Long farmerId;
    private final Long availableDateId;
    private final String token;

    public CreateNotificationByAppointmentCreated(Object source, Long farmerId, Long availableDateId, String token) {
        super(source);
        this.farmerId = farmerId;
        this.availableDateId = availableDateId;
        this.token = token;
    }

}

