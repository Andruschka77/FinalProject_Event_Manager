package dev.sorokin.eventmanager.model.domain;

import dev.sorokin.eventmanager.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private final Long id;

    private final String login;

    private final Integer age;

    private final UserRole role;

}