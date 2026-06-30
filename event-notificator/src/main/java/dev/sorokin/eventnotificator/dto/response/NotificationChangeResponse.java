package dev.sorokin.eventnotificator.dto.response;

public record NotificationChangeResponse(
        String field,
        Object oldValue,
        Object newValue
) {
}