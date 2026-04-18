package dev.sorokin.eventmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCredentialsRequest(
        @NotBlank
        @Size(min = 3, max = 15)
        String login,

        @NotBlank
        @Size(min = 5)
        String password
) {
}