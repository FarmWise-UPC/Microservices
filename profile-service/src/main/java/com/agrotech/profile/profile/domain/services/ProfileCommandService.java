package com.agrotech.profile.profile.domain.services;

import com.agrotech.profile.profile.domain.model.aggregates.Profile;
import com.agrotech.profile.profile.domain.model.commands.CreateProfileCommand;
import com.agrotech.profile.profile.domain.model.commands.DeleteProfileCommand;
import com.agrotech.profile.profile.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Long handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command);
    void handle(DeleteProfileCommand command);
}
