package com.agrotech.appointment.appointment.application.internal.eventhandlers;

import com.agrotech.appointment.appointment.application.internal.outboundservices.acl.ExternalNotificationsService;
import com.agrotech.appointment.appointment.application.internal.outboundservices.acl.ExternalProfilesService;
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
    private final ExternalProfilesService externalProfilesService;
    private final ExternalNotificationsService externalNotificationsService;
    private final AvailableDateCommandService availableDateCommandService;
    private final AvailableDateQueryService availableDateQueryService;

    public AppointmentCreatedEventHandler(ExternalProfilesService externalProfileService,
                                          ExternalNotificationsService externalNotificationsService,
                                          AvailableDateCommandService availableDateCommandService,
                                          AvailableDateQueryService availableDateQueryService) {
        this.externalProfilesService = externalProfileService;
        this.externalNotificationsService = externalNotificationsService;
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

        var farmer = externalProfilesService.fetchFarmerById(event.getFarmerId()).orElseThrow();
        var advisor = externalProfilesService.fetchAdvisorById(availableDate.getAdvisorId()).orElseThrow();
        var profileFarmer = externalProfilesService.fetchProfileByFarmerId(event.getFarmerId()).orElseThrow();
        var profileAdvisor = externalProfilesService.fetchProfileByAdvisorId(availableDate.getAdvisorId()).orElseThrow();

        var meetingUrl = "https://meet.jit.si/agrotechMeeting" + event.getFarmerId() + "-" + availableDate.getAdvisorId();

        externalNotificationsService.createNotification(farmer.getUserId(), "Proximo Asesoramiento",
                "Tienes un asesoramiento programado con " + profileAdvisor.getFirstName() + " " + profileAdvisor.getLastName(),
                date);
        externalNotificationsService.createNotification(advisor.getUserId(), "Proximo Asesoramiento",
                "Tienes una asesoria programada con " + profileFarmer.getFirstName() + " " + profileFarmer.getLastName(),
                date);
    }
}