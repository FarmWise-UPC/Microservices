package com.agrotech.iamservice.application.internal.commandservices;

import com.agrotech.iamservice.iam.application.internal.commandservices.RoleCommandServiceImpl;
import com.agrotech.iamservice.iam.domain.model.commands.SeedRolesCommand;
import com.agrotech.iamservice.iam.domain.model.valueobjects.Roles;
import com.agrotech.iamservice.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class RoleCommandServiceImplTest {
    @Test
    public void test_handle_creates_all_roles_when_none_exist() {
        // Arrange
        RoleRepository roleRepository = mock(RoleRepository.class);
        when(roleRepository.existsByName(any(Roles.class))).thenReturn(false);

        RoleCommandServiceImpl roleCommandService = new RoleCommandServiceImpl(roleRepository);
        SeedRolesCommand command = new SeedRolesCommand();

        // Act
        roleCommandService.handle(command);

        // Assert
        for (Roles role : Roles.values()) {
            verify(roleRepository).existsByName(role);
            verify(roleRepository).save(argThat(r -> r.getName() == role));
        }
    }
}