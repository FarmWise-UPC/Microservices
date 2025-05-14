package com.agrotech.profile.profile.interfaces.rest.transform;

import com.agrotech.profile.profile.domain.model.commands.UpdateProfileCommand;
import com.agrotech.profile.profile.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommandFromResource(Long id, UpdateProfileResource resource) {
        return new UpdateProfileCommand(
                id,
                resource.firstName(),
                resource.lastName(),
                resource.city(),
                resource.country(),
                resource.birthDate(),
                resource.description(),
                resource.photo(),
                resource.occupation(),
                resource.experience());
    }
}
