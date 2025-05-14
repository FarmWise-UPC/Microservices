package com.agrotech.iamservice.iam.domain.services;

import com.agrotech.iamservice.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}