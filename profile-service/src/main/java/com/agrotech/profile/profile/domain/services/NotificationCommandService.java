package com.agrotech.profile.profile.domain.services;

import com.agrotech.profile.profile.domain.model.commands.CreateNotificationCommand;
import com.agrotech.profile.profile.domain.model.commands.DeleteNotificationCommand;

public interface NotificationCommandService {
    Long handle(CreateNotificationCommand command, String token);
    void handle(DeleteNotificationCommand command);
}
