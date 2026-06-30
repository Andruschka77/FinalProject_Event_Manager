package dev.sorokin.eventnotificator.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class NotificationPayload {

    private final String messageId;

    private final String eventType;

    private final LocalDateTime occurredTime;

    private final Long changedById;

    private final Long ownerId;

    private final String eventName;

    private final List<NotificationChange> changes;

}