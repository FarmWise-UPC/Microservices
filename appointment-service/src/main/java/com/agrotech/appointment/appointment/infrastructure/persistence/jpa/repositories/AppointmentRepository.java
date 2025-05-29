package com.agrotech.appointment.appointment.infrastructure.persistence.jpa.repositories;

import com.agrotech.appointment.appointment.domain.model.aggregates.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAdvisorId(Long advisorId);
    List<Appointment> findByFarmerId(Long farmerId);
    List<Appointment> findByAdvisorIdAndFarmerId(Long advisorId, Long farmerId);
}
