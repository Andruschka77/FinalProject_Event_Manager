package dev.sorokin.eventmanager.model.entity;

import dev.sorokin.eventmanager.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity owner;

    @OneToMany(mappedBy = "event")
    private List<RegistrationEntity> registrations = new ArrayList<>();

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "cost_tickets", nullable = false)
    private Integer costTickets;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "max_places", nullable = false)
    private Integer maxPlaces;

    @Column(name = "occupied_places", nullable = false)
    private Integer occupiedPlaces;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    public EventEntity(
            String name,
            LocationEntity location,
            UserEntity owner,
            LocalDateTime startAt,
            Integer costTickets,
            Integer durationMinutes,
            Integer maxPlaces,
            Integer occupiedPlaces,
            EventStatus status
    ) {
        this.name = name;
        this.location = location;
        this.owner = owner;
        this.startAt = startAt;
        this.costTickets = costTickets;
        this.durationMinutes = durationMinutes;
        this.maxPlaces = maxPlaces;
        this.occupiedPlaces = occupiedPlaces;
        this.status = status;
    }

}