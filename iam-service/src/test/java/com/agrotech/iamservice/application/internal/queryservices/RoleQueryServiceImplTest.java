package com.agrotech.iamservice.application.internal.queryservices;

import static org.junit.jupiter.api.Assertions.*;


import com.agrotech.iamservice.iam.application.internal.queryservices.RoleQueryServiceImpl;
import com.agrotech.iamservice.iam.domain.model.entities.Role;
import com.agrotech.iamservice.iam.domain.model.queries.GetAllRolesQuery;
import com.agrotech.iamservice.iam.domain.model.queries.GetRoleByNameQuery;
import com.agrotech.iamservice.iam.domain.model.valueobjects.Roles;
import com.agrotech.iamservice.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

class RoleQueryServiceImplTest {

    @Test
    public void test_handle_get_all_roles_query_returns_all_roles() {
        // Arrange
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        List<Role> expectedRoles = List.of(
                new Role(Roles.ROLE_ADMIN),
                new Role(Roles.ROLE_USER)
        );
        Mockito.when(roleRepository.findAll()).thenReturn(expectedRoles);

        RoleQueryServiceImpl roleQueryService = new RoleQueryServiceImpl(roleRepository);
        GetAllRolesQuery query = new GetAllRolesQuery();

        // Act
        List<Role> actualRoles = roleQueryService.handle(query);

        // Assert
        assertEquals(expectedRoles, actualRoles);
        Mockito.verify(roleRepository).findAll();
    }

    @Test
    public void test_handle_get_all_roles_query_with_null_parameter() {
        // Arrange
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        List<Role> expectedRoles = List.of(
                new Role(Roles.ROLE_USER)
        );
        Mockito.when(roleRepository.findAll()).thenReturn(expectedRoles);

        RoleQueryServiceImpl roleQueryService = new RoleQueryServiceImpl(roleRepository);

        // Act
        List<Role> actualRoles = roleQueryService.handle((GetAllRolesQuery) null);

        // Assert
        assertEquals(expectedRoles, actualRoles);
        Mockito.verify(roleRepository).findAll();
    }

    @Test
    public void test_handle_get_role_by_name_query_returns_correct_role() {
        // Arrange
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        Role expectedRole = new Role(Roles.ROLE_ADMIN);
        Mockito.when(roleRepository.findByName(Roles.ROLE_ADMIN)).thenReturn(Optional.of(expectedRole));

        RoleQueryServiceImpl roleQueryService = new RoleQueryServiceImpl(roleRepository);
        GetRoleByNameQuery query = new GetRoleByNameQuery(Roles.ROLE_ADMIN);

        // Act
        Optional<Role> actualRole = roleQueryService.handle(query);

        // Assert
        assertTrue(actualRole.isPresent());
        assertEquals(expectedRole, actualRole.get());
        Mockito.verify(roleRepository).findByName(Roles.ROLE_ADMIN);
    }

    @Test
    public void test_handle_get_role_by_name_query_with_non_existing_role() {
        // Arrange
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        Mockito.when(roleRepository.findByName(Roles.ROLE_ADMIN)).thenReturn(Optional.empty());

        RoleQueryServiceImpl roleQueryService = new RoleQueryServiceImpl(roleRepository);
        GetRoleByNameQuery query = new GetRoleByNameQuery(Roles.ROLE_ADMIN);

        // Act
        Optional<Role> actualRole = roleQueryService.handle(query);

        // Assert
        assertFalse(actualRole.isPresent());
        Mockito.verify(roleRepository).findByName(Roles.ROLE_ADMIN);
    }
}