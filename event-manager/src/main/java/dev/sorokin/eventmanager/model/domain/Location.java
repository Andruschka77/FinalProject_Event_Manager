package dev.sorokin.eventmanager.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {

    private final Long id;

    private final String name;

    private final String address;

    private final Integer capacity;

    private final String description;

}