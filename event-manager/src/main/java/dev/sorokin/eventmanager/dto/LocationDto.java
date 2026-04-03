package dev.sorokin.eventmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDto {

    @Null
    private final Long id;

    @NotBlank
    private final String name;

    @NotBlank
    private final String address;

    @NotNull
    @PositiveOrZero
    private final Integer capacity;

    @NotBlank
    private final String description;

}