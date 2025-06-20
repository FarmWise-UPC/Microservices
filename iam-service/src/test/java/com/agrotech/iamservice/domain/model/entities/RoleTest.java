package com.agrotech.iamservice.domain.model.entities;


import com.agrotech.iamservice.iam.domain.exceptions.InvalidRoleException;
import com.agrotech.iamservice.iam.domain.model.entities.Role;
import com.agrotech.iamservice.iam.domain.model.valueobjects.Roles;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    public void test_valid_role_name_conversion() {
        // Given
        String roleName = "ROLE_ADMIN";

        // When
        Role role = Role.toRoleFromName(roleName);

        // Then
        assertNotNull(role);
        assertEquals(Roles.ROLE_ADMIN, role.getName());
    }

    @Test
    public void test_null_role_name_throws_exception() {
        // Given
        String roleName = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            Role.toRoleFromName(roleName);
        });
    }

    @Test
    public void test_invalid_role_name_throws_exception() {
        // Given
        String roleName = "INVALID_ROLE";

        // When & Then
        assertThrows(InvalidRoleException.class, () -> {
            Role.toRoleFromName(roleName);
        });
    }

    @Test
    public void test_create_role_with_valid_enum_value() {
        // Arrange
        Roles validRole = Roles.ROLE_ADMIN;

        // Act
        Role role = new Role(validRole);

        // Assert
        assertEquals(validRole, role.getName());
        assertEquals("ROLE_ADMIN", role.getStringName());
    }

    @Test
    public void test_get_default_role() {
        // Act
        Role defaultRole = Role.getDefaultRole();

        // Assert
        assertNotNull(defaultRole);
        assertEquals(Roles.ROLE_USER, defaultRole.getName());
    }

    @Test
    public void test_validate_role_set_with_empty_list() {
        // Act
        var validatedRoles = Role.validateRoleSet(List.of());

        // Assert
        assertEquals(1, validatedRoles.size());
        assertEquals(Roles.ROLE_USER, validatedRoles.get(0).getName());
    }

    @Test
    public void test_validate_role_set_with_valid_roles() {
        // Arrange
        var roles = List.of(new Role(Roles.ROLE_ADMIN), new Role(Roles.ROLE_USER));

        // Act
        var validatedRoles = Role.validateRoleSet(roles);

        // Assert
        assertEquals(2, validatedRoles.size());
        assertEquals(Roles.ROLE_ADMIN, validatedRoles.get(0).getName());
        assertEquals(Roles.ROLE_USER, validatedRoles.get(1).getName());
    }
}