package com.agrotech.management.management.domain.services;


import com.agrotech.management.management.domain.model.aggregates.Enclosure;
import com.agrotech.management.management.domain.model.queries.GetAllEnclosuresByFarmerIdQuery;
import com.agrotech.management.management.domain.model.queries.GetAllEnclosuresQuery;
import com.agrotech.management.management.domain.model.queries.GetEnclosureByIdQuery;

import java.util.List;
import java.util.Optional;

public interface EnclosureQueryService {
    List<Enclosure> handle(GetAllEnclosuresQuery query);
    Optional<Enclosure> handle(GetEnclosureByIdQuery query);
    List<Enclosure> handle(GetAllEnclosuresByFarmerIdQuery query);
}
