package com.agrotech.management.management.interfaces.rest.transform;


import com.agrotech.management.management.domain.model.commands.UpdateAnimalCommand;
import com.agrotech.management.management.interfaces.rest.resources.UpdateAnimalResource;

public class UpdateAnimalCommandFromResourceAssembler {
    public static UpdateAnimalCommand toCommandFromResource(Long id, UpdateAnimalResource resource) {
        return new UpdateAnimalCommand(
                id,
                resource.name(),
                resource.age(),
                resource.species(),
                resource.breed(),
                resource.gender(),
                resource.weight(),
                resource.health()
        );
    }
}
