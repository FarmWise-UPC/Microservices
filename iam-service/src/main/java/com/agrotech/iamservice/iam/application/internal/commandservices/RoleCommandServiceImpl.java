package com.agrotech.iamservice.iam.application.internal.commandservices;

import com.agrotech.iamservice.iam.domain.model.commands.SeedRolesCommand;
import com.agrotech.iamservice.iam.domain.model.entities.Role;
import com.agrotech.iamservice.iam.domain.model.valueobjects.Roles;
import com.agrotech.iamservice.iam.domain.services.RoleCommandService;
import com.agrotech.iamservice.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Implementation of {@link RoleCommandService} to handle {@link SeedRolesCommand}
 */
@Service
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;
    private final boolean enableRoleSeeding;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
        this.enableRoleSeeding = Boolean.parseBoolean(System.getenv().getOrDefault("SEED_ROLES", "true"));
    }

    /**
     * This method will handle the {@link SeedRolesCommand} and will create the roles if not exists
     * @param command {@link SeedRolesCommand}
     * @see SeedRolesCommand
     */
    @Override
    public void handle(SeedRolesCommand command) {
        if (enableRoleSeeding) {
            System.out.println("Role seeding is disabled via environment variable.");
            return;
        }

        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });
    }
}