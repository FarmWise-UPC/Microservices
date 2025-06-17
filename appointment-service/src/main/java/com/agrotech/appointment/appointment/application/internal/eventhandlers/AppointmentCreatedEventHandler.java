package com.agrotech.appointment.appointment.application.internal.eventhandlers;

import com.agrotech.appointment.appointment.application.internal.outboundservices.profile.ExternalProfileService;
import com.agrotech.appointment.appointment.domain.exceptions.AvailableDateNotFoundException;
import com.agrotech.appointment.appointment.domain.model.commands.UpdateAvailableDateStatusCommand;
import com.agrotech.appointment.appointment.domain.model.events.CreateNotificationByAppointmentCreated;
import com.agrotech.appointment.appointment.domain.model.queries.GetAvailableDateByIdQuery;
import com.agrotech.appointment.appointment.domain.services.AvailableDateCommandService;
import com.agrotech.appointment.appointment.domain.services.AvailableDateQueryService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class AppointmentCreatedEventHandler {
    private final ExternalProfileService externalProfilesService;
    private final AvailableDateCommandService availableDateCommandService;
    private final AvailableDateQueryService availableDateQueryService;

    public AppointmentCreatedEventHandler(ExternalProfileService externalProfileService,
                                          AvailableDateCommandService availableDateCommandService,
                                          AvailableDateQueryService availableDateQueryService) {
        this.externalProfilesService = externalProfileService;
        this.availableDateCommandService = availableDateCommandService;
        this.availableDateQueryService = availableDateQueryService;
    }

    @EventListener
    @Transactional
    public void onAppointmentCreated(CreateNotificationByAppointmentCreated event) {
        Date date = new Date();

        var availableDate = availableDateQueryService.handle(new GetAvailableDateByIdQuery(event.getAvailableDateId()))
                .orElseThrow(() -> new AvailableDateNotFoundException(event.getAvailableDateId()));

        availableDateCommandService.handle(new UpdateAvailableDateStatusCommand(event.getAvailableDateId(),"UNAVAILABLE"));

        var farmer = externalProfilesService.fetchFarmerById(event.getFarmerId(), event.getToken()).orElseThrow();
        var advisor = externalProfilesService.fetchAdvisorById(availableDate.getAdvisorId(), event.getToken()).orElseThrow();
        var profileFarmer = externalProfilesService.fetchProfileByUserId(farmer.userId(), event.getToken()).orElseThrow();
        var profileAdvisor = externalProfilesService.fetchProfileByUserId(advisor.userId(), event.getToken()).orElseThrow();

        externalProfilesService.createNotification(farmer.userId(), "Proximo Asesoramiento",
                "Tienes un asesoramiento programado con " + profileAdvisor.firstName() + " " + profileAdvisor.lastName(),
                date, event.getToken());
        externalProfilesService.createNotification(advisor.userId(), "Proximo Asesoramiento",
                "Tienes una asesoria programada con " + profileFarmer.firstName() + " " + profileFarmer.lastName(),
                date, event.getToken());
    }
}