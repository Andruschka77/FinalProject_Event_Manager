package dev.sorokin.eventmanager.controller;

import dev.sorokin.eventmanager.dto.response.EventResponse;
import dev.sorokin.eventmanager.mapper.EventDtoMapper;
import dev.sorokin.eventmanager.service.AuthService;
import dev.sorokin.eventmanager.service.RegistrationEventService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events/registrations")
@Slf4j
public class RegistrationEventController {

    private final RegistrationEventService registrationEventService;
    private final AuthService authService;
    private final EventDtoMapper eventDtoMapper;

    public RegistrationEventController(
            RegistrationEventService registrationEventService,
            AuthService authService,
            EventDtoMapper eventDtoMapper
    ) {
        this.registrationEventService = registrationEventService;
        this.authService = authService;
        this.eventDtoMapper = eventDtoMapper;
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<Void> registrationEvent(
            @PathVariable("eventId") Long eventId
    ) {
        log.info("User registration request for the event has been received eventId={}", eventId);

        var currentUser = authService.getCurrentAuthenticatedUserOrThrow();
        registrationEventService.registrationEvent(eventId, currentUser.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/cancel/{eventId}")
    public ResponseEntity<Void> cancelRegistrationOnEvent(
            @PathVariable("eventId") Long eventId
    ) {
        log.info("Get request to cancel registration for the event, eventId={}", eventId);

        var currentUser = authService.getCurrentAuthenticatedUserOrThrow();
        registrationEventService.cancelRegistrationOnEvent(eventId, currentUser.getId());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventResponse>> getMyEvents(
            @RequestParam(name = "pageNum", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "2") @Min(2) @Max(20) Integer pageSize
    ) {
        log.info("Get request has been received to search for all event registrations");

        var currentUser = authService.getCurrentAuthenticatedUserOrThrow();
        List<EventResponse> myEvents = registrationEventService
                .findMyEvents(
                        currentUser.getId(),
                        pageNumber,
                        pageSize
                )
                .stream()
                .map(eventDtoMapper::toResponse)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(myEvents);
    }

}