package com.agrotech.appointment.appointment.interfaces.rest.transform;

import com.agrotech.appointment.appointment.domain.model.entities.Review;
import com.agrotech.appointment.appointment.interfaces.rest.resources.ReviewResource;

public class ReviewResourceFromEntityAssembler {
    public static ReviewResource toResourceFromEntity(Review entity){
        return new ReviewResource(
                entity.getId(),
                entity.getAdvisorId(),
                entity.getFarmerId(),
                entity.getComment(),
                entity.getRating()
        );
    }
}
