package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.dto.request.EventCreateRequest;
import dev.sorokin.eventmanager.dto.request.EventUpdateRequest;
import dev.sorokin.eventmanager.dto.response.EventResponse;
import dev.sorokin.eventmanager.model.domain.Event;
import dev.sorokin.eventmanager.model.enums.EventStatus;
import org.springframework.stereotype.Component;

@Component
public class EventDtoMapper {

    public Event toDomainCreate(EventCreateRequest eventCreateRequest, Long ownerId) {
        return new Event(
                null,
                eventCreateRequest.name(),
                eventCreateRequest.locationId(),
                ownerId,
                eventCreateRequest.date(),
                eventCreateRequest.cost(),
                eventCreateRequest.duration(),
                eventCreateRequest.maxPlaces(),
                0,
                EventStatus.WAIT_START
        );
    }

    public Event toDomainUpdate(EventUpdateRequest eventUpdateRequest, Long ownerId) {
        return new Event(
                null,
                eventUpdateRequest.name(),
                eventUpdateRequest.locationId(),
                ownerId,
                eventUpdateRequest.date(),
                eventUpdateRequest.cost(),
                eventUpdateRequest.duration(),
                eventUpdateRequest.maxPlaces(),
                0,
                EventStatus.WAIT_START
        );
    }

    public EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getOwnerId(),
                event.getMaxPlaces(),
                event.getOccupiedPlaces(),
                event.getStartAt().toString(),
                event.getCostTickets(),
                event.getDurationMinutes(),
                event.getLocationId(),
                event.getStatus()
        );
    }

}
