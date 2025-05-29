package com.agrotech.appointment.appointment.infrastructure.persistence.jpa.repositories;

import com.agrotech.appointment.appointment.domain.model.entities.AvailableDate;
import com.agrotech.appointment.appointment.domain.model.valueobjects.AvailableDateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long> {
    List<AvailableDate> findByAdvisorId(Long advisorId);
    List<AvailableDate> findByAdvisorIdAndStatus(Long advisor_id, AvailableDateStatus status);
    List<AvailableDate> findByStatus(AvailableDateStatus availableDateStatus);
    Optional<AvailableDate> findByAdvisorIdAndScheduledDateAndStartTimeAndEndTime(Long advisorId, LocalDate scheduledDate, String startTime, String endTime
    );
}
