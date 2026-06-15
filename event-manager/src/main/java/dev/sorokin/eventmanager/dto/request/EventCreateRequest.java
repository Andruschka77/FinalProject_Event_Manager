package dev.sorokin.eventmanager.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record EventCreateRequest(
        @NotBlank
        String name,

        @NotNull
        @Positive
        Integer maxPlaces,

        @NotNull
        @Future(message = "The event can't be organized in the past")
        LocalDateTime date,

        @NotNull
        @Min(1)
        Integer cost,

        @NotNull
        @Min(30)
        Integer duration,

        @NotNull
        @Positive
        Long locationId
) {
}