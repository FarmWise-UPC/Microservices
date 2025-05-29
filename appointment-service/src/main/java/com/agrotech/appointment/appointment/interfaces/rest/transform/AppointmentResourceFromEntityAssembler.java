package com.agrotech.appointment.appointment.interfaces.rest.transform;

import com.agrotech.appointment.appointment.domain.model.aggregates.Appointment;
import com.agrotech.appointment.appointment.interfaces.rest.resources.AppointmentResource;

public class AppointmentResourceFromEntityAssembler {
    public static AppointmentResource toResourceFromEntity(Appointment entity){
        return new AppointmentResource(
                entity.getId(),
                entity.getFarmerId(),
                entity.getAvailableDateId(),
                entity.getMessage(),
                entity.getAppointmentStatus(),
                entity.getMeetingUrl()
        );
    }
}
