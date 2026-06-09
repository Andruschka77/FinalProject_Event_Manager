package dev.sorokin.eventmanager.model.domain;

import dev.sorokin.eventmanager.model.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Event {

    private final Long id;

    private final String name;

    private final Long locationId;

    private final Long ownerId;

    private final LocalDateTime startAt;

    private final Integer costTickets;

    private final Integer durationMinutes;

    private final Integer maxPlaces;

    private final Integer occupiedPlaces;

    private final EventStatus status;

}