package com.agrotech.appointment.appointment.domain.services;

import com.agrotech.appointment.appointment.domain.model.aggregates.Appointment;
import com.agrotech.appointment.appointment.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface AppointmentQueryService {
    List<Appointment> handle(GetAllAppointmentsQuery query);
    Optional<Appointment> handle(GetAppointmentByIdQuery query);
    List<Appointment> handle(GetAppointmentsByFarmerIdQuery query);
    List<Appointment> handle(GetAppointmentsByAdvisorIdQuery query);
    List<Appointment> handle(GetAppointmentsByAdvisorIdAndFarmerIdQuery query);
}
