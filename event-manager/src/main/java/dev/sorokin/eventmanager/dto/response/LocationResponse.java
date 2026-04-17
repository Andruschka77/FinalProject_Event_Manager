package dev.sorokin.eventmanager.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationResponse {

    @Null
    private final Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    private final String name;

    @NotBlank
    @Size(min = 3, max = 60)
    private final String address;

    @NotNull
    @PositiveOrZero
    private final Integer capacity;

    @Size(min = 5)
    private final String description;

}