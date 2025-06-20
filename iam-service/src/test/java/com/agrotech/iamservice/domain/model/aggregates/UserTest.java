package com.agrotech.iamservice.domain.model.aggregates;

import com.agrotech.iamservice.iam.domain.model.aggregates.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    public void test_user_constructor_initializes_empty_collections() {
        // Arrange
        String username = "test@example.com";
        String password = "password123";

        // Act
        User user = new User(username, password);

        // Assert
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().isEmpty());
    }
}