package com.agrotech.profile.profile.infrastructure.persistence.jpa.repositories;

import com.agrotech.profile.profile.domain.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
}
