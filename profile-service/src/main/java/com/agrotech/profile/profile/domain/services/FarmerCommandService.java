package com.agrotech.profile.profile.domain.services;

import com.agrotech.profile.profile.domain.model.commands.CreateFarmerCommand;
import com.agrotech.profile.profile.domain.model.commands.DeleteFarmerCommand;

public interface FarmerCommandService {
    Long handle(CreateFarmerCommand createFarmerCommand);
    void handle(DeleteFarmerCommand command);
}
