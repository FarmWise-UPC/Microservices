package com.agrotech.appointment.appointment.domain.model.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateNotificationByAppointmentCancelled extends ApplicationEvent {
    private final Long availableDateId;
    private final String token;

    public CreateNotificationByAppointmentCancelled(Object source, Long availableDateId, String token) {
        super(source);
        this.availableDateId = availableDateId;
        this.token = token;
    }
}
