package com.agrotech.appointment.appointment.interfaces.rest.transform;

import com.agrotech.appointment.appointment.domain.model.commands.CreateAppointmentCommand;
import com.agrotech.appointment.appointment.interfaces.rest.resources.CreateAppointmentResource;

public class CreateAppointmentCommandFromResourceAssembler {
    public static CreateAppointmentCommand toCommandFromResource(CreateAppointmentResource resource){
        return new CreateAppointmentCommand(
                resource.availableDateId(),
                resource.farmerId(),
                resource.message()
        );
    }
}
