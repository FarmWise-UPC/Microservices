package com.agrotech.profile.profile.application.internal.commandservices;

import com.agrotech.profile.profile.application.internal.outboundservices.acl.ExternalUserService;
import com.agrotech.profile.profile.domain.exceptions.ProfileNotFoundException;
import com.agrotech.profile.profile.domain.exceptions.UserAlreadyUsedException;
import com.agrotech.profile.profile.domain.model.aggregates.Profile;
import com.agrotech.profile.profile.domain.model.commands.*;
import com.agrotech.profile.profile.domain.services.AdvisorCommandService;
import com.agrotech.profile.profile.domain.services.FarmerCommandService;
import com.agrotech.profile.profile.domain.services.ProfileCommandService;
import com.agrotech.profile.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import com.agrotech.profile.shared.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;
    private final ExternalUserService externalUserService;
    private final FarmerCommandService farmerCommandService;
    private final AdvisorCommandService advisorCommandService;


    public ProfileCommandServiceImpl(ProfileRepository profileRepository, ExternalUserService externalUserService, FarmerCommandService farmerCommandService, AdvisorCommandService advisorCommandService) {
        this.profileRepository = profileRepository;
        this.externalUserService = externalUserService;
        this.farmerCommandService = farmerCommandService;
        this.advisorCommandService = advisorCommandService;
    }

    @Override
    public Long handle(CreateProfileCommand command, String token) {
        var userOpt = externalUserService.fetchUserById(command.userId(), token);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException(command.userId());
        }
        var sameUser = profileRepository.findByUserId(command.userId());
        if (sameUser.isPresent()) {
            throw new UserAlreadyUsedException(command.userId());
        }

        var user = userOpt.get();
        var roles = user.roles();

        if (roles != null) {
            if (java.util.Arrays.asList(roles).contains("ROLE_FARMER")) {
                farmerCommandService.handle(new CreateFarmerCommand(command.userId()));
            }
            if (java.util.Arrays.asList(roles).contains("ROLE_ADVISOR")) {
                advisorCommandService.handle(new CreateAdvisorCommand(command.userId()));
            }
        }

        Profile profile = new Profile(command, command.userId());
        profileRepository.save(profile);
        return profile.getId();
    }

    @Override
    public Optional<Profile> handle(UpdateProfileCommand command) {
        var profile = profileRepository.findById(command.id());
        if (profile.isEmpty()) return Optional.empty();
        var profileToUpdate = profile.get();
        Profile updatedProfile = profileRepository.save(profileToUpdate.update(command));
        return Optional.of(updatedProfile);
    }

    @Override
    public void handle(DeleteProfileCommand command) {
        var profile = profileRepository.findById(command.id());
        if (profile.isEmpty()) {
            throw new ProfileNotFoundException(command.id());
        }
        profileRepository.delete(profile.get());
    }
}