package dev.sorokin.eventnotificator.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationChange {

    private final String field;

    private final Object oldValue;

    private final Object newValue;

}