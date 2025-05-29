package com.agrotech.appointment.appointment.interfaces.rest.transform;

import com.agrotech.appointment.appointment.domain.model.commands.CreateAvailableDateCommand;
import com.agrotech.appointment.appointment.interfaces.rest.resources.CreateAvailableDateResource;

public class CreateAvailableDateCommandFromResourceAssembler {
    public static CreateAvailableDateCommand toCommandFromResource(CreateAvailableDateResource resource){
        return new CreateAvailableDateCommand(
                resource.advisorId(),
                resource.scheduledDate(),
                resource.startTime(),
                resource.endTime()
        );
    }
}
