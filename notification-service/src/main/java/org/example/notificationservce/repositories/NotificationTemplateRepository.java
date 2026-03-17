package org.example.notificationservce.repositories;

import org.example.notificationservce.models.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTemplateRepository
        extends JpaRepository<NotificationTemplate, Long> {

    Optional<NotificationTemplate> findByEventType(String eventType);
}