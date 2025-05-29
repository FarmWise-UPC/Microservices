package com.agrotech.appointment.appointment.interfaces.rest.transform;

import com.agrotech.appointment.appointment.domain.model.commands.CreateReviewCommand;
import com.agrotech.appointment.appointment.interfaces.rest.resources.CreateReviewResource;

public class CreateReviewCommandFromResourceAssembler {
    public static CreateReviewCommand toCommandFromResource(CreateReviewResource resource){
        return new CreateReviewCommand(
                resource.advisorId(),
                resource.farmerId(),
                resource.comment(),
                resource.rating()
        );
    }
}
