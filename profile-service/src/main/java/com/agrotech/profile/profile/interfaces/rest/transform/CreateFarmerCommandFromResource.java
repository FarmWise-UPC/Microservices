package com.agrotech.profile.profile.interfaces.rest.transform;

import com.agrotech.profile.profile.domain.model.commands.CreateFarmerCommand;
import com.agrotech.profile.profile.interfaces.rest.resources.CreateFarmerResource;

public class CreateFarmerCommandFromResource {
    public static CreateFarmerCommand toCommandFromResource(CreateFarmerResource resource) {
        return new CreateFarmerCommand(resource.userId());
    }
}
