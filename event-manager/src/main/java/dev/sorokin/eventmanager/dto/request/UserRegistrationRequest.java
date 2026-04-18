package dev.sorokin.eventmanager.dto.request;

import jakarta.validation.constraints.*;

public record UserRegistrationRequest(
        @NotBlank
        @Size(min = 3, max = 15)
        String login,

        @NotBlank
        @Size(min = 5)
        String password,

        @NotNull
        @Positive
        @Min(3)
        Integer age
) {
}