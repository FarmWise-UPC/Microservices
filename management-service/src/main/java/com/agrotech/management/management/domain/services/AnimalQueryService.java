package com.agrotech.management.management.domain.services;

import com.agrotech.management.management.domain.model.entities.Animal;
import com.agrotech.management.management.domain.model.queries.GetAllAnimalsByEnclosureIdQuery;
import com.agrotech.management.management.domain.model.queries.GetAllAnimalsQuery;
import com.agrotech.management.management.domain.model.queries.GetAnimalByIdQuery;

import java.util.List;
import java.util.Optional;

public interface AnimalQueryService {
    List<Animal> handle(GetAllAnimalsQuery query);
    Optional<Animal> handle(GetAnimalByIdQuery query);
    List<Animal> handle(GetAllAnimalsByEnclosureIdQuery query);
}
