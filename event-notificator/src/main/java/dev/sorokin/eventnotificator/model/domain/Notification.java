package dev.sorokin.eventnotificator.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Notification {

    private final Long notificationId;

    private final String type;

    private final Long eventId;

    private final LocalDateTime createdAt;

    private final Boolean isRead;

    private final String message;

    private final NotificationPayload payload;

}