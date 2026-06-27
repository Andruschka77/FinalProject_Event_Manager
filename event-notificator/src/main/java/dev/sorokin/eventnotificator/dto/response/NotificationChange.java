package dev.sorokin.eventnotificator.dto.response;

public record NotificationChange(
        String field,
        Object oldValue,
        Object newValue
) {
}