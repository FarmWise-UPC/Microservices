package com.agrotech.profile.profile.application.internal.queryservices;

import com.agrotech.profile.profile.domain.model.aggregates.Profile;
import com.agrotech.profile.profile.domain.model.queries.GetAllAdvisorProfilesQuery;
import com.agrotech.profile.profile.domain.model.queries.GetAllProfilesQuery;
import com.agrotech.profile.profile.domain.model.queries.GetProfileByIdQuery;
import com.agrotech.profile.profile.domain.model.queries.GetProfileByUserIdQuery;
import com.agrotech.profile.profile.domain.services.ProfileQueryService;
import com.agrotech.profile.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.id());
    }

    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }

    @Override
    public Optional<Profile> handle(GetProfileByUserIdQuery query) {
        return profileRepository.findByUserId(query.userId());
    }

    @Override
    public List<Profile> handle(GetAllAdvisorProfilesQuery query){
        return profileRepository.findAllAdvisorProfiles();
    }
}
