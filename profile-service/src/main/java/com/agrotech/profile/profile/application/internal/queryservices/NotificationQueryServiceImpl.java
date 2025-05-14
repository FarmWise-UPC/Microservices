package com.agrotech.profile.profile.application.internal.queryservices;

import com.agrotech.profile.profile.domain.model.entities.Notification;
import com.agrotech.profile.profile.domain.model.queries.GetAllNotificationsQuery;
import com.agrotech.profile.profile.domain.model.queries.GetNotificationByIdQuery;
import com.agrotech.profile.profile.domain.model.queries.GetNotificationsByUserIdQuery;
import com.agrotech.profile.profile.domain.services.NotificationQueryService;
import com.agrotech.profile.profile.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return notificationRepository.findById(query.id());
    }

    @Override
    public List<Notification> handle(GetAllNotificationsQuery query) {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> handle(GetNotificationsByUserIdQuery query) {
        return notificationRepository.findByUser_Id(query.userId());
    }
}
