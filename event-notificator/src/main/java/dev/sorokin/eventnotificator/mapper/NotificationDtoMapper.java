package dev.sorokin.eventnotificator.mapper;

import dev.sorokin.eventnotificator.dto.response.NotificationResponse;
import dev.sorokin.eventnotificator.model.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationDtoMapper {

    public NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse(
                notification.getNotificationId(),
                notification.getType(),
                notification.getEventId(),
                notification.getCreatedAt(),
                notification.getIsRead(),
                notification.getMessage(),
                notification.getPayload()
        );
    }

}