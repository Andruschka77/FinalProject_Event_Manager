package dev.sorokin.eventmanager.dto.request;

import jakarta.validation.constraints.*;

public record LocationRequest(
        @Null
        Long id,

        @NotBlank
        @Size(min = 3, max = 20)
        String name,

        @NotBlank
        @Size(min = 3, max = 60)
        String address,

        @NotNull
        @PositiveOrZero
        Integer capacity,

        @Size(min = 5)
        String description
) {
}