package com.agrotech.profile.profile.domain.services;

import com.agrotech.iamservice.iam.domain.model.aggregates.User;
import com.agrotech.profile.profile.domain.model.commands.CreateFarmerCommand;
import com.agrotech.profile.profile.domain.model.commands.DeleteFarmerCommand;

public interface FarmerCommandService {
    Long handle(CreateFarmerCommand createFarmerCommand,User user);
    void handle(DeleteFarmerCommand command);
}
