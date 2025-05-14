package com.agrotech.profile.profile.domain.services;

import com.agrotech.profile.profile.domain.model.aggregates.Profile;
import com.agrotech.profile.profile.domain.model.queries.GetAllAdvisorProfilesQuery;
import com.agrotech.profile.profile.domain.model.queries.GetAllProfilesQuery;
import com.agrotech.profile.profile.domain.model.queries.GetProfileByIdQuery;
import com.agrotech.profile.profile.domain.model.queries.GetProfileByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByIdQuery query);
    List<Profile> handle(GetAllProfilesQuery query);
    Optional<Profile> handle(GetProfileByUserIdQuery query);
    List<Profile> handle(GetAllAdvisorProfilesQuery query);
}
