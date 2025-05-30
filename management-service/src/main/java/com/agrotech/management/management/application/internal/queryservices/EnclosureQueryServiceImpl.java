package com.agrotech.management.management.application.internal.queryservices;



import com.agrotech.management.management.domain.model.aggregates.Enclosure;
import com.agrotech.management.management.domain.model.queries.GetAllEnclosuresByFarmerIdQuery;
import com.agrotech.management.management.domain.model.queries.GetAllEnclosuresQuery;
import com.agrotech.management.management.domain.model.queries.GetEnclosureByIdQuery;
import com.agrotech.management.management.domain.services.EnclosureQueryService;
import com.agrotech.management.management.infrastructure.persitence.jpa.repositories.EnclosureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnclosureQueryServiceImpl implements EnclosureQueryService {
    private final EnclosureRepository enclosureRepository;

    public EnclosureQueryServiceImpl(EnclosureRepository enclosureRepository) {
        this.enclosureRepository = enclosureRepository;
    }

    @Override
    public List<Enclosure> handle(GetAllEnclosuresQuery query) {
        return this.enclosureRepository.findAll();
    }

    @Override
    public Optional<Enclosure> handle(GetEnclosureByIdQuery query) {
        return this.enclosureRepository.findById(query.enclosureId());
    }

    @Override
    public List<Enclosure> handle(GetAllEnclosuresByFarmerIdQuery query) {
        return this.enclosureRepository.findAllByFarmerId(query.farmerId());
    }
}
