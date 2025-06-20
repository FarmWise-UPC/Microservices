package com.agrotech.appointment.applicacion.internal.commandservices;


import com.agrotech.appointment.appointment.application.internal.commandservices.AvailableDateCommandServiceImpl;
import com.agrotech.appointment.appointment.application.internal.outboundservices.profile.ExternalProfileService;
import com.agrotech.appointment.appointment.domain.exceptions.AdvisorNotFoundException;
import com.agrotech.appointment.appointment.domain.model.commands.CreateAvailableDateCommand;
import com.agrotech.appointment.appointment.domain.model.entities.AvailableDate;
import com.agrotech.appointment.appointment.infrastructure.persistence.jpa.repositories.AvailableDateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AvailableDateCommandServiceImplTest {
    @Mock
    private AvailableDateRepository availableDateRepository;

    @Mock
    private ExternalProfileService externalProfilesService;

    @InjectMocks
    private AvailableDateCommandServiceImpl availableDateCommandService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_handle_create_available_date_with_advisor_not_found() {
        // Arrange
        Long advisorId = 1L;
        LocalDate scheduledDate = LocalDate.now().plusDays(1);
        String startTime = "10:00";
        String endTime = "11:00";
        String token = "valid-token";

        CreateAvailableDateCommand command = new CreateAvailableDateCommand(advisorId, scheduledDate, startTime, endTime);

        when(externalProfilesService.fetchAdvisorById(advisorId, token)).thenReturn(Optional.empty());

        // Act & Assert
        AdvisorNotFoundException exception = assertThrows(AdvisorNotFoundException.class, () -> {
            availableDateCommandService.handle(command, token);
        });

        // Verify
        assertEquals("Advisor with id " + advisorId + " not found", exception.getMessage());
        verify(externalProfilesService).fetchAdvisorById(advisorId, token);
        verify(availableDateRepository, never()).save(any(AvailableDate.class));
    }
}