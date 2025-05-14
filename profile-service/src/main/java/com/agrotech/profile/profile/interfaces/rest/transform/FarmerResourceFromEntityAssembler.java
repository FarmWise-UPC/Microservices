package com.agrotech.profile.profile.interfaces.rest.transform;

import com.agrotech.profile.profile.domain.model.entities.Farmer;
import com.agrotech.profile.profile.interfaces.rest.resources.FarmerResource;

public class FarmerResourceFromEntityAssembler {
    public static FarmerResource toResourceFromEntity(Farmer entity) {
        return new FarmerResource(
                entity.getId(),
                entity.getUserId());
    }
}
