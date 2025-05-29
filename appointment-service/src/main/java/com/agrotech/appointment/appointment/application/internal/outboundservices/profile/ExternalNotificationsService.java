package com.agrotech.appointment.appointment.application.internal.outboundservices.profile;

import com.agrotech.appointment.appointment.infrastructure.outbound.profile.rest.NotificationRestClient;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExternalNotificationsService {
    private final NotificationRestClient notificationRestClient;

    public ExternalNotificationsService(NotificationRestClient notificationRestClient) {
        this.notificationRestClient = notificationRestClient;
    }

    public void createNotification(Long userId, String title, String message, Date sendAt) {
        notificationRestClient.sendNotification(userId, title, message, sendAt);
    }
}