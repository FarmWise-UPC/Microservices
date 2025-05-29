package com.agrotech.appointment.appointment.interfaces.rest.transform;

import com.agrotech.appointment.appointment.domain.model.commands.UpdateReviewCommand;
import com.agrotech.appointment.appointment.interfaces.rest.resources.UpdateReviewResource;

public class UpdateReviewCommandFromResourceAssembler {
    public static UpdateReviewCommand toCommandFromResource(Long id, UpdateReviewResource resource){
        return new UpdateReviewCommand(
                id,
                resource.comment(),
                resource.rating()
        );
    }
}
