package com.agrotech.profile.profile.application.internal.commandservices;

import com.agrotech.profile.profile.application.internal.outboundservices.acl.ExternalUserService;
import com.agrotech.profile.profile.domain.exceptions.ProfileNotFoundException;
import com.agrotech.profile.profile.domain.exceptions.UserAlreadyUsedException;
import com.agrotech.profile.profile.domain.model.aggregates.Profile;
import com.agrotech.profile.profile.domain.model.commands.CreateProfileCommand;
import com.agrotech.profile.profile.domain.model.commands.DeleteProfileCommand;
import com.agrotech.profile.profile.domain.model.commands.UpdateProfileCommand;
import com.agrotech.profile.profile.domain.services.ProfileCommandService;
import com.agrotech.profile.profile.infrastructure.persistence.jpa.repositories.ProfileRepository;
import com.agrotech.profile.shared.domain.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {
    private final ProfileRepository profileRepository;
    private final ExternalUserService externalUserService;

    public ProfileCommandServiceImpl(ProfileRepository profileRepository, ExternalUserService externalUserService) {
        this.profileRepository = profileRepository;
        this.externalUserService = externalUserService;
    }

    @Override
    public Long handle(CreateProfileCommand command) {
        var user = externalUserService.fetchUserById(command.userId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(command.userId());
        }
        var sameUser = profileRepository.findByUser_Id(command.userId());
        if (sameUser.isPresent()) {
            throw new UserAlreadyUsedException(command.userId());
        }
        Profile profile = new Profile(command, user.get());
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