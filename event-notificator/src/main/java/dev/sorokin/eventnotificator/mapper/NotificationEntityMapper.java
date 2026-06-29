package dev.sorokin.eventnotificator.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sorokin.eventnotificator.model.domain.Notification;
import dev.sorokin.eventnotificator.model.domain.NotificationChange;
import dev.sorokin.eventnotificator.model.domain.NotificationPayload;
import dev.sorokin.eventnotificator.model.entity.NotificationEntity;
import dev.sorokin.eventnotificator.model.entity.NotificationEventPayloadEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class NotificationEntityMapper {

    private final ObjectMapper objectMapper;

    public NotificationEntityMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Notification toDomain(NotificationEntity notificationEntity) {

        NotificationEventPayloadEntity payloadEntity = notificationEntity.getPayload();

        Map<String, Object> payloadMap;
        try {
            payloadMap = objectMapper.readValue(
                    payloadEntity.getChanges(),
                    new TypeReference<>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to build payload JSON", e);
        }

        List<NotificationChange> changes = null;
        if (payloadMap.containsKey("changes")) {
            changes = objectMapper.convertValue(
                    payloadMap.get("changes"),
                    new TypeReference<>() {
                    }
            );
        }

        return new Notification(
                notificationEntity.getNotificationId(),
                payloadEntity.getEventType(),
                payloadEntity.getEventId(),
                notificationEntity.getCreatedAt(),
                notificationEntity.getIsRead(),
                generateMessage(payloadEntity.getEventType()),
                new NotificationPayload(
                        payloadEntity.getMessageId(),
                        payloadEntity.getEventType(),
                        payloadEntity.getOccurredAt(),
                        payloadEntity.getChangedById(),
                        payloadEntity.getOwnerId(),
                        payloadEntity.getEventName(),
                        changes
                )
        );
    }

    private String generateMessage(String eventType) {
        return switch (eventType) {
            case "EVENT_UPDATED" -> "Событие было изменено";
            case "EVENT_DELETED" -> "Событие было отменено";
            default -> "Событие было обновлено";
        };
    }

}