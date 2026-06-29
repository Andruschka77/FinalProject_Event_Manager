package dev.sorokin.eventnotificator.dto.response;

import dev.sorokin.eventnotificator.model.domain.NotificationPayload;
import java.time.LocalDateTime;

public record NotificationResponse(
        Long notificationId,
        String type,
        Long eventId,
        LocalDateTime createdAt,
        Boolean isRead,
        String message,
        NotificationPayload payload
) {
}