package com.agrotech.management.management.interfaces.rest.transform;


import com.agrotech.management.management.domain.model.commands.UpdateEnclosureCommand;
import com.agrotech.management.management.interfaces.rest.resources.UpdateEnclosureResource;

public class UpdateEnclosureCommandFromResourceAssembler {
    public static UpdateEnclosureCommand toCommandFromResource(Long id, UpdateEnclosureResource resource) {
        return new UpdateEnclosureCommand(
                id,
                resource.name(),
                resource.capacity(),
                resource.type()
        );
    }
}
