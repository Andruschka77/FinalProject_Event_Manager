package dev.sorokin.eventmanager.dto.request;

import dev.sorokin.eventmanager.model.enums.EventStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record EventSearchRequest(
        @NotBlank
        String name,

        @NotNull
        @Positive
        Integer placesMin,

        @NotNull
        @Positive
        Integer placesMax,

        @NotNull
        LocalDateTime dateStartAfter,

        @NotNull
        LocalDateTime dateStartBefore,

        @NotNull
        @Min(20)
        Integer costMin,

        @NotNull
        @Max(600_000)
        Integer costMax,

        @NotNull
        @Min(20)
        Integer durationMin,

        @NotNull
        @Max(300)
        Integer durationMax,

        @NotNull
        @Positive
        Long locationId,

        EventStatus eventStatus
) {
}