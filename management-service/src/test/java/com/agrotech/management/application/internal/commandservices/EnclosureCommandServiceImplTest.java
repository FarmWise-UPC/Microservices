package com.agrotech.management.application.internal.commandservices;

import com.agrotech.management.management.application.internal.commandservices.EnclosureCommandServiceImpl;
import com.agrotech.management.management.domain.model.aggregates.Enclosure;
import com.agrotech.management.management.domain.model.commands.CreateEnclosureCommand;
import com.agrotech.management.management.infrastructure.outbound.profile.dtos.FarmerView;
import com.agrotech.management.management.infrastructure.outbound.profile.rest.ProfileRestClient;
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
import static org.mockito.Mockito.*;

class EnclosureCommandServiceImplTest {
    @Mock
    private EnclosureRepository enclosureRepository;
    @Mock
    private ProfileRestClient profileRestClient;
    @InjectMocks
    private EnclosureCommandServiceImpl enclosureCommandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void test_handle_create_enclosure_command_returns_enclosure_id() {
        // Arrange
        Long farmerId = 1L;
        Long enclosureId = 1L;
        String name = "Cattle Pen";
        Integer capacity = 50;
        String type = "Cattle";
        String token = "valid-token";

        CreateEnclosureCommand command = new CreateEnclosureCommand(name, capacity, type, farmerId);

        Enclosure enclosure = Mockito.mock(Enclosure.class);
        FarmerView farmerView = new FarmerView(farmerId, 100L);

        when(profileRestClient.getFarmerById(farmerId, token)).thenReturn(Optional.of(farmerView));
        when(enclosure.getId()).thenReturn(enclosureId);
        when(enclosureRepository.save(any(Enclosure.class))).thenReturn(enclosure);

        // Act
        Long result = enclosureCommandService.handle(command, token);

        // Assert
        assertEquals(enclosureId, result);
        verify(profileRestClient).getFarmerById(farmerId, token);
        verify(enclosureRepository).save(any(Enclosure.class));
    }
}