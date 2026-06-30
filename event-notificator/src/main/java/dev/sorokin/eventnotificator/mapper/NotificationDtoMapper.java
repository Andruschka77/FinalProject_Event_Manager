package dev.sorokin.eventnotificator.mapper;

import dev.sorokin.eventnotificator.dto.response.NotificationChangeResponse;
import dev.sorokin.eventnotificator.dto.response.NotificationPayloadResponse;
import dev.sorokin.eventnotificator.dto.response.NotificationResponse;
import dev.sorokin.eventnotificator.model.domain.Notification;
import dev.sorokin.eventnotificator.model.domain.NotificationChange;
import dev.sorokin.eventnotificator.model.domain.NotificationPayload;
import org.springframework.stereotype.Component;
import java.util.List;

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
                toPayloadResponse(notification.getPayload())
        );
    }

    private NotificationPayloadResponse toPayloadResponse(NotificationPayload payload) {
        return new NotificationPayloadResponse(
                payload.getMessageId(),
                payload.getEventType(),
                payload.getOccurredTime(),
                payload.getChangedById(),
                payload.getOwnerId(),
                payload.getEventName(),
                toChangeResponse(payload.getChanges())
        );
    }

    private List<NotificationChangeResponse> toChangeResponse(List<NotificationChange> changes) {
        return changes
                .stream()
                .map(x -> new NotificationChangeResponse(
                        x.getField(),
                        x.getOldValue(),
                        x.getNewValue()
                ))
                .toList();
    }

}