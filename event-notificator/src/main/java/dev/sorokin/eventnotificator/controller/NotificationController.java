package dev.sorokin.eventnotificator.controller;

import dev.sorokin.eventnotificator.dto.request.MarkNotificationsAsReadRequest;
import dev.sorokin.eventnotificator.dto.response.NotificationResponse;
import dev.sorokin.eventnotificator.mapper.NotificationDtoMapper;
import dev.sorokin.eventnotificator.service.AuthService;
import dev.sorokin.eventnotificator.service.NotificationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;
    private final AuthService authService;
    private final NotificationDtoMapper notificationDtoMapper;

    public NotificationController(
            NotificationService notificationService,
            AuthService authService,
            NotificationDtoMapper notificationDtoMapper
    ) {
        this.notificationService = notificationService;
        this.authService = authService;
        this.notificationDtoMapper = notificationDtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> findNotificationsByCurrentUser() {
        log.info("A request has been received to receive notifications from the current user");

        List<NotificationResponse> notifications = notificationService
                .findNotificationsByCurrentUser(
                    authService.getIdCurrentAuthenticatedUserOrThrow()
                )
                .stream()
                .map(notificationDtoMapper::toResponse)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notifications);
    }

    @PostMapping
    public ResponseEntity<Void> markNotificationsAsRead(
        @Valid @RequestBody MarkNotificationsAsReadRequest readRequest
    ) {
        log.info("A request has been received to read all notifications for the current user");

        notificationService.markNotificationsAsRead(
                readRequest,
                authService.getIdCurrentAuthenticatedUserOrThrow()
        );

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}