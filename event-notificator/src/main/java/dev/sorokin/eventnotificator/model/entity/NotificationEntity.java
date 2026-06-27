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
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Long notificationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payload_id", referencedColumnName = "payload_id", nullable = false)
    private NotificationEventPayloadEntity payload;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    public NotificationEntity(
            Long userId,
            NotificationEventPayloadEntity payload,
            Boolean isRead,
            LocalDateTime createdAt,
            LocalDateTime readAt
    ) {
        this.userId = userId;
        this.payload = payload;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.readAt = readAt;
    }

}