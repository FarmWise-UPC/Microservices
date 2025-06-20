package com.agrotech.appointment.domain.model.aggregates;

import com.agrotech.appointment.appointment.domain.model.aggregates.Appointment;
import com.agrotech.appointment.appointment.domain.model.commands.CreateAppointmentCommand;
import com.agrotech.appointment.appointment.domain.model.entities.AvailableDate;
import com.agrotech.appointment.appointment.domain.model.valueobjects.AppointmentStatus;
import com.agrotech.appointment.appointment.infrastructure.outbound.profile.dtos.FarmerView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppointmentTest {
    @Test
    public void test_create_appointment_with_valid_command_sets_correct_values() {
        // Arrange
        Long availableDateId = 1L;
        Long farmerId = 2L;
        Long advisorId = 3L;
        String message = "I would like to schedule an appointment";
        String meetingUrl = "http://meeting.url";

        CreateAppointmentCommand command = new CreateAppointmentCommand(availableDateId, farmerId, message);

        FarmerView farmerView = mock(FarmerView.class);
        when(farmerView.userId()).thenReturn(farmerId);

        AvailableDate availableDate = mock(AvailableDate.class);
        when(availableDate.getId()).thenReturn(availableDateId);

        // Act
        Appointment appointment = new Appointment(command, meetingUrl, farmerId, advisorId, availableDate);

        // Assert
        assertEquals(message, appointment.getMessage());
        assertEquals(AppointmentStatus.PENDING, appointment.getStatus());
        assertEquals(availableDate, appointment.getAvailableDate());
        assertNotNull(appointment.getMeetingUrl());
        assertEquals(farmerId, appointment.getFarmerId());
        assertEquals(availableDateId, appointment.getAvailableDateId());
        assertEquals("PENDING", appointment.getAppointmentStatus());
    }
}