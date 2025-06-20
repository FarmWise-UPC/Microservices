package com.agrotech.appointment.applicacion.internal.commandservices;

import com.agrotech.appointment.appointment.application.internal.commandservices.AppointmentCommandServiceImpl;
import com.agrotech.appointment.appointment.application.internal.outboundservices.profile.ExternalProfileService;
import com.agrotech.appointment.appointment.domain.exceptions.AvailableDateNotFoundException;
import com.agrotech.appointment.appointment.domain.model.aggregates.Appointment;
import com.agrotech.appointment.appointment.domain.model.commands.CreateAppointmentCommand;
import com.agrotech.appointment.appointment.domain.model.entities.AvailableDate;
import com.agrotech.appointment.appointment.domain.model.events.CreateNotificationByAppointmentCreated;
import com.agrotech.appointment.appointment.domain.model.queries.GetAvailableDateByIdQuery;
import com.agrotech.appointment.appointment.domain.model.valueobjects.AppointmentStatus;
import com.agrotech.appointment.appointment.domain.model.valueobjects.AvailableDateStatus;
import com.agrotech.appointment.appointment.domain.services.AvailableDateQueryService;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.AdvisorView;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.FarmerView;
import com.agrotech.appointment.appointment.infrastructure.persistence.jpa.repositories.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class AppointmentCommandServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private ExternalProfileService externalProfilesService;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private AvailableDateQueryService availableDateQueryService;

    @InjectMocks
    private AppointmentCommandServiceImpl appointmentCommandService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_handle_create_appointment_command_success() {
        // Arrange
        Long availableDateId = 1L;
        Long farmerId = 2L;
        Long advisorId = 3L;
        Long farmerUserId = 20L;
        Long advisorUserId = 30L;
        BigDecimal advisorRating = BigDecimal.valueOf(4.5);
        String message = "Appointment request";
        Long appointmentId = 10L;
        String expectedMeetingUrl = "https://meet.jit.si/agrotechMeeting" + farmerId + "-" + advisorId;
        String token = "valid-token";

        CreateAppointmentCommand command = new CreateAppointmentCommand(availableDateId, farmerId, message);

        AvailableDate availableDate = mock(AvailableDate.class);
        when(availableDate.getStatus()).thenReturn(AvailableDateStatus.AVAILABLE);
        when(availableDate.getAdvisorId()).thenReturn(advisorId);
        when(availableDate.getId()).thenReturn(availableDateId);

        AdvisorView advisorView = new AdvisorView(advisorId, advisorUserId, advisorRating);
        FarmerView farmerView = new FarmerView(farmerId, farmerUserId);

        when(availableDateQueryService.handle(
                argThat((GetAvailableDateByIdQuery query) ->
                        query.id().equals(availableDateId)
                )
        )).thenReturn(Optional.of(availableDate));

        when(externalProfilesService.fetchAdvisorById(advisorId, token)).thenReturn(Optional.of(advisorView));
        when(externalProfilesService.fetchFarmerById(farmerId, token)).thenReturn(Optional.of(farmerView));

        ArgumentCaptor<Appointment> appointmentCaptor = ArgumentCaptor.forClass(Appointment.class);
        when(appointmentRepository.save(appointmentCaptor.capture())).thenAnswer(invocation -> {
            Appointment savedAppointment = invocation.getArgument(0);
            ReflectionTestUtils.setField(savedAppointment, "id", appointmentId);
            return savedAppointment;
        });

        // Act
        Long result = appointmentCommandService.handle(command, token);

        // Assert
        assertEquals(appointmentId, result);

        Appointment capturedAppointment = appointmentCaptor.getValue();
        assertEquals(message, capturedAppointment.getMessage());
        assertEquals(AppointmentStatus.PENDING, capturedAppointment.getStatus());
        assertEquals(farmerId, capturedAppointment.getFarmerId());
        assertEquals(advisorId, capturedAppointment.getAdvisorId());
        assertEquals(availableDate, capturedAppointment.getAvailableDate());
        assertEquals(expectedMeetingUrl, capturedAppointment.getMeetingUrl());

        verify(appointmentRepository).save(any(Appointment.class));
        verify(eventPublisher).publishEvent(argThat(event ->
                event instanceof CreateNotificationByAppointmentCreated &&
                        ((CreateNotificationByAppointmentCreated) event).getFarmerId().equals(farmerId) &&
                        ((CreateNotificationByAppointmentCreated) event).getAvailableDateId().equals(availableDateId)));
    }

    @Test
    public void test_handle_create_appointment_command_with_nonexistent_available_date() {
        // Arrange
        Long availableDateId = 1L;
        Long farmerId = 2L;
        String message = "Appointment request";

        CreateAppointmentCommand command = new CreateAppointmentCommand(availableDateId, farmerId, message);

        when(availableDateQueryService.handle(any(GetAvailableDateByIdQuery.class))).thenReturn(Optional.empty());

        // Act & Assert
        String token = "valid-token";
        assertThrows(AvailableDateNotFoundException.class, () -> {
            appointmentCommandService.handle(command, token);
        });

        verify(availableDateQueryService).handle(any(GetAvailableDateByIdQuery.class));
        verifyNoInteractions(appointmentRepository);
        verifyNoInteractions(eventPublisher);
    }
}