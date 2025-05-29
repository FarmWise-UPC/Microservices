package com.agrotech.appointment.appointment.interfaces.rest.transform;

import com.agrotech.appointment.appointment.domain.model.entities.AvailableDate;
import com.agrotech.appointment.appointment.interfaces.rest.resources.AvailableDateResource;

public class AvailableDateResourceFromEntityAssembler {
    public static AvailableDateResource toResourceFromEntity(AvailableDate entity){
        return new AvailableDateResource(
                entity.getId(),
                entity.getAdvisorId(),
                entity.getScheduledDate(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getAvailableDateStatus()
        );
    }
}
