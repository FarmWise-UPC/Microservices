package com.agrotech.appointment.appointment.application.internal.commandservices;

import com.agrotech.appointment.appointment.application.internal.outboundservices.profile.ExternalProfileService;
import com.agrotech.appointment.appointment.domain.exceptions.*;
import com.agrotech.appointment.appointment.domain.model.aggregates.Appointment;
import com.agrotech.appointment.appointment.domain.model.commands.CreateAppointmentCommand;
import com.agrotech.appointment.appointment.domain.model.commands.DeleteAppointmentCommand;
import com.agrotech.appointment.appointment.domain.model.commands.UpdateAppointmentCommand;
import com.agrotech.appointment.appointment.domain.model.events.CreateNotificationByAppointmentCancelled;
import com.agrotech.appointment.appointment.domain.model.events.CreateNotificationByAppointmentCreated;
import com.agrotech.appointment.appointment.domain.model.queries.GetAvailableDateByIdQuery;
import com.agrotech.appointment.appointment.domain.model.valueobjects.AppointmentStatus;
import com.agrotech.appointment.appointment.domain.model.valueobjects.AvailableDateStatus;
import com.agrotech.appointment.appointment.domain.services.AppointmentCommandService;
import com.agrotech.appointment.appointment.domain.services.AvailableDateQueryService;
import com.agrotech.appointment.appointment.infrastructure.persistence.jpa.repositories.AppointmentRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentCommandServiceImpl implements AppointmentCommandService {
    private final AppointmentRepository appointmentRepository;
    private final ExternalProfileService externalProfilesService;
    private final ApplicationEventPublisher eventPublisher;
    private final AvailableDateQueryService availableDateQueryService;

    public AppointmentCommandServiceImpl(AppointmentRepository appointmentRepository,
                                         ExternalProfileService externalProfilesService,
                                         ApplicationEventPublisher eventPublisher,
                                         AvailableDateQueryService availableDateQueryService) {
        this.appointmentRepository = appointmentRepository;
        this.externalProfilesService = externalProfilesService;
        this.eventPublisher = eventPublisher;
        this.availableDateQueryService = availableDateQueryService;
    }

    @Override
    @Transactional
    public Long handle(CreateAppointmentCommand command, String token) {
        var availableDate = availableDateQueryService.handle(new GetAvailableDateByIdQuery(command.availableDateId()));
        if (availableDate.isEmpty()) throw new AvailableDateNotFoundException(command.availableDateId());
        if (availableDate.get().getStatus() == AvailableDateStatus.UNAVAILABLE) {
            throw new InvalidAvailableDateException(command.availableDateId());
        }
        var advisor = externalProfilesService.fetchAdvisorById(availableDate.get().getAdvisorId(), token);
        if (advisor.isEmpty()) throw new AdvisorNotFoundException(availableDate.get().getAdvisorId());
        var farmer = externalProfilesService.fetchFarmerById(command.farmerId(), token);
        if (farmer.isEmpty()) throw new FarmerNotFoundException(command.farmerId());

        var meetingUrl = "https://meet.jit.si/agrotechMeeting" + command.farmerId() + "-" + availableDate.get().getAdvisorId();

        Appointment appointment = new Appointment(command, meetingUrl, command.farmerId(), availableDate.get().getAdvisorId(), availableDate.get());
        appointmentRepository.save(appointment);
        eventPublisher.publishEvent(new CreateNotificationByAppointmentCreated(this,
                command.farmerId(), command.availableDateId(), token));
        return appointment.getId();
    }

    @Override
    public Optional<Appointment> handle(UpdateAppointmentCommand command) {
        var appointment = appointmentRepository.findById(command.id());
        if (appointment.isEmpty()) return Optional.empty();
        // Verification of Status
        if (command.status() != null && !command.status().matches("^(?i)(PENDING|ONGOING|COMPLETED)$")) {
            throw new InvalidStatusException(command.status());
        }
        var appointmentToUpdate = appointment.get();
        Appointment updatedAppointment = appointmentRepository.save(appointmentToUpdate.update(command));
        return Optional.of(updatedAppointment);
    }

    @Override
    @Transactional
    public void handle(DeleteAppointmentCommand command, String token) {
        var appointment = appointmentRepository.findById(command.id());
        if (appointment.isEmpty()) throw new AppointmentNotFoundException(command.id());
        var availableDate = availableDateQueryService.handle(new GetAvailableDateByIdQuery(appointment.get().getAvailableDateId()));
        if (availableDate.isEmpty()) throw new AvailableDateNotFoundException(appointment.get().getAvailableDateId());
        eventPublisher.publishEvent(new CreateNotificationByAppointmentCancelled(this, availableDate.get().getId(), token));
        appointmentRepository.delete(appointment.get());
    }

    public void updateAppointmentStatuses(List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            updateAppointmentStatus(appointment);
        }
    }

    public void updateAppointmentStatus(Appointment appointment) {
        var availableDate = availableDateQueryService.handle(new GetAvailableDateByIdQuery(appointment.getAvailableDateId()));
        if (availableDate.isEmpty()) throw new AvailableDateNotFoundException(appointment.getAvailableDateId());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.of(availableDate.get().getScheduledDate(), LocalTime.parse(availableDate.get().getStartTime()));
        LocalDateTime end = LocalDateTime.of(availableDate.get().getScheduledDate(), LocalTime.parse(availableDate.get().getEndTime()));

        // Determine the new status
        String newStatus;
        if (now.isAfter(end)) {
            newStatus = AppointmentStatus.COMPLETED.name();
        } else if (now.isAfter(start) && now.isBefore(end)) {
            newStatus = AppointmentStatus.ONGOING.name();
        } else {
            newStatus = appointment.getAppointmentStatus();
        }

        // Update the status if it has changed
        if (!appointment.getAppointmentStatus().equals(newStatus)) {
            var updateCommand = new UpdateAppointmentCommand(
                    appointment.getId(),
                    appointment.getMessage(),
                    newStatus
            );

            appointment.update(updateCommand);
            appointmentRepository.save(appointment);
        }
    }
}
