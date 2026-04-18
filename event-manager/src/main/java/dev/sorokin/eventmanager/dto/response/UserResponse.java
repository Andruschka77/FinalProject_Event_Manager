package dev.sorokin.eventmanager.dto.response;

import dev.sorokin.eventmanager.model.enums.UserRole;

public record UserResponse(
        Long id,
        String login,
        Integer age,
        UserRole role
) {
}