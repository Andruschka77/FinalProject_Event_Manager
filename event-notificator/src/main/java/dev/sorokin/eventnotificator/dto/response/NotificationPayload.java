package dev.sorokin.eventnotificator.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record NotificationPayload(
        String messageId,
        String eventType,
        LocalDateTime occurredTime,
        Long changedById,
        Long ownerId,
        String eventName,
        List<NotificationChange> changes
) {
}