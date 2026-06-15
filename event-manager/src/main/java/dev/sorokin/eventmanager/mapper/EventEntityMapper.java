package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.model.domain.Event;
import dev.sorokin.eventmanager.model.entity.EventEntity;
import dev.sorokin.eventmanager.model.entity.LocationEntity;
import dev.sorokin.eventmanager.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class EventEntityMapper {

    public Event toDomain(EventEntity eventEntity) {
        return new Event(
                eventEntity.getId(),
                eventEntity.getName(),
                eventEntity.getLocation().getId(),
                eventEntity.getOwner().getId(),
                eventEntity.getStartAt(),
                eventEntity.getCostTickets(),
                eventEntity.getDurationMinutes(),
                eventEntity.getMaxPlaces(),
                eventEntity.getOccupiedPlaces(),
                eventEntity.getStatus()
        );
    }

    public EventEntity toEntity(
            Event event,
            LocationEntity location,
            UserEntity owner
    ) {
        return new EventEntity(
                event.getName(),
                location,
                owner,
                event.getStartAt(),
                event.getCostTickets(),
                event.getDurationMinutes(),
                event.getMaxPlaces(),
                event.getOccupiedPlaces(),
                event.getStatus()
        );
    }

}
