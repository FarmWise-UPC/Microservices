package com.agrotech.profile.profile.application.internal.queryservices;

import com.agrotech.profile.profile.domain.model.entities.Farmer;
import com.agrotech.profile.profile.domain.model.queries.GetAllFarmersQuery;
import com.agrotech.profile.profile.domain.model.queries.GetFarmerByIdQuery;
import com.agrotech.profile.profile.domain.model.queries.GetFarmerByUserIdQuery;
import com.agrotech.profile.profile.domain.services.FarmerQueryService;
import com.agrotech.profile.profile.infrastructure.persistence.jpa.repositories.FarmerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmerQueryServiceImpl implements FarmerQueryService {
    private final FarmerRepository farmerRepository;

    public FarmerQueryServiceImpl(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Override
    public Optional<Farmer> handle(GetFarmerByIdQuery query) {
        return farmerRepository.findById(query.id());
    }

    @Override
    public Optional<Farmer> handle(GetFarmerByUserIdQuery query) {
        return farmerRepository.findByUserId(query.userId());
    }

    @Override
    public List<Farmer> handle(GetAllFarmersQuery query) {
        return farmerRepository.findAll();
    }

}
