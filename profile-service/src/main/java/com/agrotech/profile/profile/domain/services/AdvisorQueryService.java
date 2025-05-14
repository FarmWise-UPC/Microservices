package com.agrotech.profile.profile.domain.services;

import com.agrotech.profile.profile.domain.model.entities.Advisor;
import com.agrotech.profile.profile.domain.model.queries.GetAdvisorByIdQuery;
import com.agrotech.profile.profile.domain.model.queries.GetAdvisorByUserIdQuery;
import com.agrotech.profile.profile.domain.model.queries.GetAllAdvisorsQuery;

import java.util.List;
import java.util.Optional;

public interface AdvisorQueryService {
    Optional<Advisor> handle(GetAdvisorByIdQuery query);
    List<Advisor> handle(GetAllAdvisorsQuery query);
    Optional<Advisor> handle(GetAdvisorByUserIdQuery query);
}
