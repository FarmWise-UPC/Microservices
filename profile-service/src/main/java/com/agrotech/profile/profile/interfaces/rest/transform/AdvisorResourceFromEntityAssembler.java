package com.agrotech.profile.profile.interfaces.rest.transform;

import com.agrotech.profile.profile.domain.model.entities.Advisor;
import com.agrotech.profile.profile.interfaces.rest.resources.AdvisorResource;

public class AdvisorResourceFromEntityAssembler {
    public static AdvisorResource toResourceFromEntity(Advisor entity) {
        return new AdvisorResource(
                entity.getId(),
                entity.getUserId(),
                entity.getRating());
    }
}
