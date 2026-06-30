package dev.sorokin.eventnotificator.repository;

import dev.sorokin.eventnotificator.model.entity.NotificationEventPayloadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationEventPayloadRepository extends JpaRepository<NotificationEventPayloadEntity, Long> {

    boolean existsByMessageId(String messageId);

}