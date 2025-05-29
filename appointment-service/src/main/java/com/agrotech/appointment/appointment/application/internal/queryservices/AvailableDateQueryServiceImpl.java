package com.agrotech.appointment.appointment.application.internal.queryservices;

import com.agrotech.appointment.appointment.domain.model.entities.AvailableDate;
import com.agrotech.appointment.appointment.domain.model.queries.*;
import com.agrotech.appointment.appointment.domain.model.valueobjects.AvailableDateStatus;
import com.agrotech.appointment.appointment.domain.services.AvailableDateQueryService;
import com.agrotech.appointment.appointment.infrastructure.persistence.jpa.repositories.AvailableDateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailableDateQueryServiceImpl implements AvailableDateQueryService {
    private final AvailableDateRepository availableDateRepository;

    public AvailableDateQueryServiceImpl(AvailableDateRepository availableDateRepository) {
        this.availableDateRepository = availableDateRepository;
    }

    @Override
    public List<AvailableDate> handle(GetAllAvailableDatesQuery query) {
        List<AvailableDate> availableDates = availableDateRepository.findAll();
        removePastAvailableDates(availableDates);
        return availableDates;
    }

    @Override
    public Optional<AvailableDate> handle(GetAvailableDateByIdQuery query) {
        Optional<AvailableDate> availableDate = availableDateRepository.findById(query.id());
        availableDate.ifPresent(this::removePastAvailableDate);
        return availableDate;
    }

    @Override
    public List<AvailableDate> handle(GetAvailableDatesByAdvisorIdQuery query) {
        List<AvailableDate> availableDates = availableDateRepository.findByAdvisorId(query.advisorId());
        removePastAvailableDates(availableDates);
        return availableDates;
    }

    @Override
    public List<AvailableDate> handle(GetAvailableDateByAdvisorIdAndStatusQuery query) {
        List<AvailableDate> availableDates = availableDateRepository.findByAdvisorIdAndStatus(query.advisorId(), AvailableDateStatus.valueOf(query.status()));
        removePastAvailableDates(availableDates);
        return availableDates;
    }

    @Override
    public List<AvailableDate> handle(GetAvailableDateByStatusQuery query) {
        List<AvailableDate> availableDates = availableDateRepository.findByStatus(AvailableDateStatus.valueOf(query.status()));
        removePastAvailableDates(availableDates);
        return availableDates;
    }

    @Override
    public Optional<AvailableDate> handle(GetAvailableDateByAdvisorIdAndDate query) {
        Optional<AvailableDate> availableDate = availableDateRepository.findByAdvisorIdAndScheduledDateAndStartTimeAndEndTime(
                query.advisorId(),
                query.scheduledDate(),
                query.startTime(),
                query.endTime()
        );
        availableDate.ifPresent(this::removePastAvailableDate);
        return availableDate;
    }

    private void removePastAvailableDates(List<AvailableDate> availableDates) {
        for (AvailableDate availableDate : availableDates) {
            removePastAvailableDate(availableDate);
        }
    }

    private void removePastAvailableDate(AvailableDate availableDate) {
        // Check if the available date is in the past and has not been booked
        if (availableDate.getScheduledDate().isBefore(LocalDate.now()) && availableDate.getStatus() == AvailableDateStatus.AVAILABLE ) {
            availableDateRepository.delete(availableDate);
        }
    }
}
