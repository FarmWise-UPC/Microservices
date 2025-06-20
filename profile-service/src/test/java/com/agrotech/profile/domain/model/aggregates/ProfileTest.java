package com.agrotech.profile.domain.model.aggregates;

import com.agrotech.profile.profile.domain.model.aggregates.Profile;
import com.agrotech.profile.profile.domain.model.commands.CreateProfileCommand;
import com.agrotech.profile.profile.infrastructure.outbound.user.dtos.UserView;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileTest {

    @Test
    public void test_create_farmer_profile_with_valid_command() {
        // Arrange
        Long userId = 1L;
        String username = "john_doe";
        String[] roles = {"ROLE_FARMER"};
        UserView userView = new UserView(userId, username, roles);

        String firstName = "John";
        String lastName = "Doe";
        String city = "Lima";
        String country = "Perú";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String description = "Hola soy un granjero.";
        String photo = "john_doe.jpg";
        String occupation = "";
        Integer experience = 0;

        CreateProfileCommand command = new CreateProfileCommand(
                userView.id(), firstName, lastName, city, country, birthDate, description, photo, occupation, experience
        );

        // Act
        Profile profile = new Profile(command, userView.id());

        // Assert
        assertEquals(firstName, profile.getFirstName());
        assertEquals(lastName, profile.getLastName());
        assertEquals(city, profile.getCity());
        assertEquals(country, profile.getCountry());
        assertEquals(birthDate, profile.getBirthDate());
        assertEquals(description, profile.getDescription());
        assertEquals(photo, profile.getPhoto());
        assertEquals(occupation, profile.getOccupation());
        assertEquals(experience, profile.getExperience());
        assertEquals(userView.id(), profile.getUserId());
    }

    @Test
    public void test_create_advisor_profile_with_valid_command() {
        // Arrange
        Long userId = 1L;
        String username = "mario_ramos";
        String[] roles = {"ROLE_ADVISOR"};
        UserView userView = new UserView(userId, username, roles);

        String firstName = "Mario";
        String lastName = "Ramos";
        String city = "Arequipa";
        String country = "Perú";
        LocalDate birthDate = LocalDate.of(1989, 12, 14);
        String description = "Hola soy un asesor.";
        String photo = "mario_ramos.jpg";
        String occupation = "Consultor Agrícola";
        Integer experience = 9;

        CreateProfileCommand command = new CreateProfileCommand(
                userView.id(), firstName, lastName, city, country, birthDate, description, photo, occupation, experience
        );

        // Act
        Profile profile = new Profile(command, userView.id());

        // Assert
        assertEquals(firstName, profile.getFirstName());
        assertEquals(lastName, profile.getLastName());
        assertEquals(city, profile.getCity());
        assertEquals(country, profile.getCountry());
        assertEquals(birthDate, profile.getBirthDate());
        assertEquals(description, profile.getDescription());
        assertEquals(photo, profile.getPhoto());
        assertEquals(occupation, profile.getOccupation());
        assertEquals(experience, profile.getExperience());
        assertEquals(userView.id(), profile.getUserId());
    }
}