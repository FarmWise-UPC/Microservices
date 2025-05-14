package com.agrotech.profile.profile.interfaces.rest.transform;

import com.agrotech.profile.profile.domain.model.entities.Notification;
import com.agrotech.profile.profile.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {
    public static NotificationResource toResourceFromEntity(Notification entity) {
        return new NotificationResource(
                entity.getId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getSendAt());
    }
}
