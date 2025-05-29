package com.agrotech.appointment.appointment.application.internal.commandservices;

import com.agrotech.appointment.appointment.application.internal.outboundservices.profile.ExternalProfileService;
import com.agrotech.appointment.appointment.domain.exceptions.*;
import com.agrotech.appointment.appointment.domain.model.commands.CreateAvailableDateCommand;
import com.agrotech.appointment.appointment.domain.model.commands.DeleteAvailableDateCommand;
import com.agrotech.appointment.appointment.domain.model.commands.UpdateAvailableDateCommand;
import com.agrotech.appointment.appointment.domain.model.commands.UpdateAvailableDateStatusCommand;
import com.agrotech.appointment.appointment.domain.model.entities.AvailableDate;
import com.agrotech.appointment.appointment.domain.services.AvailableDateCommandService;
import com.agrotech.appointment.appointment.infrastructure.persistence.jpa.repositories.AvailableDateRepository;
import com.agrotech.appointment.appointment.domain.exceptions.AdvisorNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AvailableDateCommandServiceImpl implements AvailableDateCommandService {
    private final AvailableDateRepository availableDateRepository;
    private final ExternalProfileService externalProfilesService;

    public AvailableDateCommandServiceImpl(AvailableDateRepository availableDateRepository, ExternalProfileService externalProfilesService) {
        this.availableDateRepository = availableDateRepository;
        this.externalProfilesService = externalProfilesService;
    }

    @Override
    public Long handle(CreateAvailableDateCommand command) {
        var advisor = externalProfilesService.fetchAdvisorById(command.advisorId());
        if(advisor.isEmpty()) throw new AdvisorNotFoundException(command.advisorId());
        var sameAvailableDate = availableDateRepository.findByAdvisorIdAndScheduledDateAndStartTimeAndEndTime(
                command.advisorId(), command.scheduledDate(), command.startTime(), command.endTime());
        if (sameAvailableDate.isPresent()) throw new SameAvailableDateException(command.scheduledDate(), command.startTime(), command.endTime());
        // Verification of the date
        if (command.scheduledDate().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new InvalidDateException(command.scheduledDate());
        }
        validateTimeFormat(command.startTime(), command.endTime());
        validateTimeRange(command.startTime(), command.endTime());
        var availableDate = new AvailableDate(command, command.advisorId());
        availableDateRepository.save(availableDate);
        return availableDate.getId();
    }

    @Override
    public Optional<AvailableDate> handle(UpdateAvailableDateCommand command) {
        var availableDate = availableDateRepository.findById(command.id());
        if(availableDate.isEmpty()) return Optional.empty();
        validateTimeFormat(command.startTime(), command.endTime());
        validateTimeRange(command.startTime(), command.endTime());
        var availableDateToUpdate = availableDate.get();
        availableDateRepository.save(availableDateToUpdate.update(command));
        return Optional.of(availableDateToUpdate);
    }

    @Override
    public void handle(DeleteAvailableDateCommand command) {
        var availableDate = availableDateRepository.findById(command.id());
        if(availableDate.isEmpty()) throw new AvailableDateNotFoundException(command.id());
        availableDateRepository.delete(availableDate.get());
    }

    @Override
    public void handle(UpdateAvailableDateStatusCommand command) {
        var availableDate = availableDateRepository.findById(command.id());
        if(availableDate.isEmpty()) throw new AvailableDateNotFoundException(command.id());
        var availableDateToUpdate = availableDate.get();
        availableDateRepository.save(availableDateToUpdate.updateStatus(command.status()));
    }

    private void validateTimeFormat(String startTime, String endTime) {
        if (!startTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$") || !endTime.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            throw new IncorrectTimeFormatException(startTime, endTime);
        }
    }

    private void validateTimeRange(String startTime, String endTime) {
        if (startTime.compareTo(endTime) >= 0) {
            throw new InvalidTimeRangeException(startTime, endTime);
        }
    }
}
