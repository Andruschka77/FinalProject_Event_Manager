package dev.sorokin.eventcommon.kafka;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EventChangeKafkaMessage(
        UUID messageId,
        String eventType,
        Long eventId,
        LocalDateTime occurredAt,
        Long ownerId,
        Long changedById,
        String eventName,
        List<Long> subscribers,
        List<ChangeItem> changes
) {
}