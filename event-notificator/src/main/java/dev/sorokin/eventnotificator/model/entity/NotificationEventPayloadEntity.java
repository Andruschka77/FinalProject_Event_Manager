package dev.sorokin.eventnotificator.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notification_event_payloads")
public class NotificationEventPayloadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payload_id", nullable = false)
    private Long payloadId;

    @Column(name = "message_id", nullable = false, unique = true)
    private String messageId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "changed_by_id")
    private Long changedById;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "payload", columnDefinition = "jsonb", nullable = false)
    private String payload;

    public NotificationEventPayloadEntity(
            String messageId,
            String eventType,
            Long eventId,
            LocalDateTime occurredAt,
            Long changedById,
            Long ownerId,
            String payload
    ) {
        this.messageId = messageId;
        this.eventType = eventType;
        this.eventId = eventId;
        this.occurredAt = occurredAt;
        this.changedById = changedById;
        this.ownerId = ownerId;
        this.payload = payload;
    }

}