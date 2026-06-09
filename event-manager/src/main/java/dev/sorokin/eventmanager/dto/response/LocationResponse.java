package dev.sorokin.eventmanager.dto.response;

import jakarta.validation.constraints.*;

public record LocationResponse(
        Long id,
        String name,
        String address,
        Integer capacity,
        String description
) {
}