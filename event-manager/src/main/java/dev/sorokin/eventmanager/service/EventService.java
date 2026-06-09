package dev.sorokin.eventmanager.service;

import dev.sorokin.eventmanager.dto.request.EventSearchRequest;
import dev.sorokin.eventmanager.exception.ResourceNotFoundException;
import dev.sorokin.eventmanager.mapper.EventEntityMapper;
import dev.sorokin.eventmanager.mapper.LocationEntityMapper;
import dev.sorokin.eventmanager.model.domain.Event;
import dev.sorokin.eventmanager.model.domain.Location;
import dev.sorokin.eventmanager.model.domain.User;
import dev.sorokin.eventmanager.model.entity.LocationEntity;
import dev.sorokin.eventmanager.model.enums.EventStatus;
import dev.sorokin.eventmanager.model.enums.UserRole;
import dev.sorokin.eventmanager.repository.EventRepository;
import dev.sorokin.eventmanager.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventEntityMapper eventEntityMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final LocationService locationService;
    private final LocationEntityMapper locationEntityMapper;

    public EventService(
            EventEntityMapper eventEntityMapper,
            EventRepository eventRepository,
            UserRepository userRepository,
            LocationService locationService,
            LocationEntityMapper locationEntityMapper
    ) {
        this.eventEntityMapper = eventEntityMapper;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.locationService = locationService;
        this.locationEntityMapper = locationEntityMapper;
    }

    public Event createEvent(Event eventToCreate) {
        Location location = locationService.findById(eventToCreate.getLocationId());
        LocationEntity locationEntity = locationEntityMapper.toEntity(location);
        locationEntity.setId(eventToCreate.getLocationId());

        if (locationEntity.getCapacity() < eventToCreate.getMaxPlaces()) {
            throw new IllegalArgumentException("Вместимость локации не позволяет провести мероприятие");
        }

        var eventToSave = eventEntityMapper.toEntity(
                eventToCreate,
                locationEntity,
                userRepository.getReferenceById(eventToCreate.getOwnerId())
        );

        return eventEntityMapper.toDomain(
                eventRepository.save(eventToSave)
        );
    }

    public Event findById(Long eventId) {
        var foundEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", eventId));
        return eventEntityMapper.toDomain(foundEvent);
    }

    @Transactional
    public Event updateEvent(Long eventId, Event eventToUpdate, User user) {
        if (user.getRole() == UserRole.USER && !user.getId().equals(eventToUpdate.getOwnerId())) {
            throw new AccessDeniedException("У вас недостаточно прав для выполнения данной операции");
        }
        var updatedEvent = eventRepository.findById(eventId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Event", eventId)
                );

        Location location = locationService.findById(eventToUpdate.getLocationId());
        LocationEntity locationEntity = locationEntityMapper.toEntity(location);
        locationEntity.setId(eventToUpdate.getLocationId());

        if (locationEntity.getCapacity() < eventToUpdate.getMaxPlaces()) {
            throw new IllegalArgumentException("Вместимость локации не позволяет провести мероприятие");
        }

        updatedEvent.setName(eventToUpdate.getName());
        updatedEvent.setLocation(locationEntity);
        updatedEvent.setStartAt(eventToUpdate.getStartAt());
        updatedEvent.setCostTickets(eventToUpdate.getCostTickets());
        updatedEvent.setDurationMinutes(eventToUpdate.getDurationMinutes());
        updatedEvent.setMaxPlaces(eventToUpdate.getMaxPlaces());
        updatedEvent.setOccupiedPlaces(eventToUpdate.getOccupiedPlaces());
        updatedEvent.setStatus(eventToUpdate.getStatus());

        return eventEntityMapper.toDomain(updatedEvent);
    }

    @Transactional
    public void deleteEvent(Long eventId, User currentUser) {
        var deletedEvent = eventRepository.findById(eventId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Event", eventId)
                );
        if (currentUser.getRole() == UserRole.USER && !currentUser.getId().equals(deletedEvent.getOwner().getId())) {
            throw new AccessDeniedException("У вас недостаточно прав для выполнения данной операции");
        }
        if (deletedEvent.getStartAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Нельзя удалить мероприятие, которое уже началось");
        }

        deletedEvent.setStatus(EventStatus.CANCELLED);
    }

    public List<Event> searchAllEventsForUser(
            Long ownerId,
            Integer pageNumber,
            Integer pageSize
    ) {
        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        return eventRepository.findEventsByOwnerId(ownerId, pageable)
                .stream()
                .map(eventEntityMapper::toDomain)
                .toList();
    }

    public List<Event> searchAllEventsByFilter(
            EventSearchRequest eventSearchRequest,
            Integer pageNumber,
            Integer pageSize
    ) {
        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        return eventRepository.findEventsByFilters(
                        eventSearchRequest.name(),
                        eventSearchRequest.placesMin(),
                        eventSearchRequest.placesMax(),
                        eventSearchRequest.dateStartAfter(),
                        eventSearchRequest.dateStartBefore(),
                        eventSearchRequest.costMin(),
                        eventSearchRequest.costMax(),
                        eventSearchRequest.durationMin(),
                        eventSearchRequest.durationMax(),
                        eventSearchRequest.locationId(),
                        eventSearchRequest.eventStatus(),
                        pageable
                )
                .stream()
                .map(eventEntityMapper::toDomain)
                .toList();
    }

}