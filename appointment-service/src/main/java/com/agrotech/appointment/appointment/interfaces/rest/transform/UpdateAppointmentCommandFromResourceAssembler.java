package com.agrotech.appointment.appointment.interfaces.rest.transform;

import com.agrotech.appointment.appointment.domain.model.commands.UpdateAppointmentCommand;
import com.agrotech.appointment.appointment.interfaces.rest.resources.UpdateAppointmentResource;

public class UpdateAppointmentCommandFromResourceAssembler {
    public static UpdateAppointmentCommand toCommandFromResource(Long id, UpdateAppointmentResource resource){
        return new UpdateAppointmentCommand(
                id,
                resource.message(),
                resource.status()
        );
    }
}
