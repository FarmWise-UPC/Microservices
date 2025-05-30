package com.agrotech.management.management.interfaces.rest.transform;


import com.agrotech.management.management.domain.model.aggregates.Enclosure;
import com.agrotech.management.management.interfaces.rest.resources.EnclosureResource;

public class EnclosureResourceFromEntityAssembler {
    public static EnclosureResource toResourceFromEntity(Enclosure entity) {
        return new EnclosureResource(
                entity.getId(),
                entity.getName(),
                entity.getCapacity(),
                entity.getType(),
                entity.getFarmerId()
        );
    }
}
