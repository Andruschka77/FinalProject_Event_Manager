package dev.sorokin.eventcommon.dto;

import java.time.LocalDateTime;

public record ErrorMessageResponse(
        String message,
        String detailedMessage,
        LocalDateTime dateTime
) {
}