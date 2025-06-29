package com.agrotech.management.application.internal.commandservices;


import com.agrotech.management.management.application.internal.commandservices.AnimalCommandServiceImpl;
import com.agrotech.management.management.domain.model.aggregates.Enclosure;
import com.agrotech.management.management.domain.model.commands.CreateAnimalCommand;
import com.agrotech.management.management.domain.model.entities.Animal;
import com.agrotech.management.management.infrastructure.persitence.jpa.repositories.AnimalRepository;
import com.agrotech.management.management.infrastructure.persitence.jpa.repositories.EnclosureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AnimalCommandServiceImplTest {
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private EnclosureRepository enclosureRepository;
    @InjectMocks
    private AnimalCommandServiceImpl animalCommandService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_handle_create_animal_command_with_valid_data_returns_animal_id() {
        // Arrange
        Long enclosureId = 1L;
        Long expectedAnimalId = 1L;

        CreateAnimalCommand command = new CreateAnimalCommand(
                "Bessie",
                3,
                "Cow",
                "Holstein",
                true,
                450.5f,
                "HEALTHY",
                enclosureId
        );

        Enclosure mockEnclosure = Mockito.mock(Enclosure.class);
        Animal mockAnimal = Mockito.mock(Animal.class);

        when(enclosureRepository.findById(enclosureId)).thenReturn(Optional.of(mockEnclosure));
        when(mockAnimal.getId()).thenReturn(expectedAnimalId);

        when(animalRepository.save(any(Animal.class))).thenReturn(mockAnimal);

        // Act
        Long actualAnimalId = animalCommandService.handle(command);

        // Assert
        assertEquals(expectedAnimalId, actualAnimalId);
        verify(animalRepository).save(any(Animal.class));
        verify(enclosureRepository).findById(enclosureId);
    }
}