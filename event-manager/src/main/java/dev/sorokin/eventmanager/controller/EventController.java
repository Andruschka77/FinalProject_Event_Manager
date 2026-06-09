package dev.sorokin.eventmanager.controller;

import dev.sorokin.eventmanager.dto.request.EventCreateRequest;
import dev.sorokin.eventmanager.dto.request.EventSearchRequest;
import dev.sorokin.eventmanager.dto.request.EventUpdateRequest;
import dev.sorokin.eventmanager.dto.response.EventResponse;
import dev.sorokin.eventmanager.mapper.EventDtoMapper;
import dev.sorokin.eventmanager.model.domain.Event;
import dev.sorokin.eventmanager.service.AuthService;
import dev.sorokin.eventmanager.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;
    private final AuthService authService;
    private final EventDtoMapper eventDtoMapper;

    public EventController(
            EventService eventService,
            AuthService authService,
            EventDtoMapper eventDtoMapper
    ) {
        this.eventService = eventService;
        this.authService = authService;
        this.eventDtoMapper = eventDtoMapper;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody @Valid EventCreateRequest eventToCreate
    ) {
        log.info("Get request for create event: event={}", eventToCreate);

        var currentUser = authService.getCurrentAuthenticatedUserOrThrow();

        Event createdEvent = eventService.createEvent(
                eventDtoMapper.toDomainCreate(eventToCreate, currentUser.getId())
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(eventDtoMapper.toResponse(createdEvent));
    }

    @PostMapping("/search")
    public ResponseEntity<List<EventResponse>> searchAllEventsByFilter(
            @RequestParam(name = "pageNum", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "2") @Min(2) @Max(20) Integer pageSize,
            @RequestBody @Valid EventSearchRequest eventSearchRequest
    ) {
        log.info("Get request for search events by filters");

        List<EventResponse> eventsByFiler = eventService
                .searchAllEventsByFilter(
                    eventSearchRequest,
                    pageNumber,
                    pageSize
                )
                .stream()
                .map(eventDtoMapper::toResponse)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventsByFiler);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> findEventById(
            @PathVariable("eventId") Long eventId
    ) {
        log.info("Get request for find event by id: eventId={}", eventId);

        Event foundEvent = eventService.findById(eventId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventDtoMapper.toResponse(foundEvent));
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventResponse>> getAllEventsForUser(
            @RequestParam(name = "pageNum", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "2") @Min(2) @Max(20) Integer pageSize
    ) {
        log.info("Get request for getAllEventsForUser");

        var currentUser = authService.getCurrentAuthenticatedUserOrThrow();

        List<EventResponse> eventsForUser = eventService
                .searchAllEventsForUser(
                        currentUser.getId(),
                        pageNumber,
                        pageSize
                )
                .stream()
                .map(eventDtoMapper::toResponse)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventsForUser);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable("eventId") Long eventId,
            @RequestBody @Valid EventUpdateRequest eventToUpdate
    ) {
        log.info("Get request for update event: eventId={}, eventToUpdate={}", eventId, eventToUpdate);

        var currentUser = authService.getCurrentAuthenticatedUserOrThrow();

        var updatedEvent = eventService.updateEvent(
                eventId,
                eventDtoMapper.toDomainUpdate(eventToUpdate, eventService.findById(eventId).getOwnerId()),
                currentUser
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(eventDtoMapper.toResponse(updatedEvent));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable("eventId") Long eventId
    ) {
        log.info("Get request for delete event by id: eventId={}", eventId);

        var currentUser = authService.getCurrentAuthenticatedUserOrThrow();

        eventService.deleteEvent(eventId, currentUser);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}