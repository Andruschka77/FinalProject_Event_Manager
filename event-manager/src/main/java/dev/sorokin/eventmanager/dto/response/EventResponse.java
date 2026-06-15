package dev.sorokin.eventmanager.dto.response;

import dev.sorokin.eventmanager.model.enums.EventStatus;

public record EventResponse(
        Long id,
        String name,
        Long ownerId,
        Integer maxPlaces,
        Integer occupiedPlaces,
        String date,
        Integer cost,
        Integer duration,
        Long locationId,
        EventStatus status
) {
}