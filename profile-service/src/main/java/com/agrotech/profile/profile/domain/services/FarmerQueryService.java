package com.agrotech.profile.profile.domain.services;


import com.agrotech.profile.profile.domain.model.entities.Farmer;
import com.agrotech.profile.profile.domain.model.queries.GetAllFarmersQuery;
import com.agrotech.profile.profile.domain.model.queries.GetFarmerByIdQuery;
import com.agrotech.profile.profile.domain.model.queries.GetFarmerByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface FarmerQueryService {
    Optional<Farmer> handle(GetFarmerByIdQuery query);
    List<Farmer> handle(GetAllFarmersQuery query);
    Optional<Farmer> handle(GetFarmerByUserIdQuery query);
}
