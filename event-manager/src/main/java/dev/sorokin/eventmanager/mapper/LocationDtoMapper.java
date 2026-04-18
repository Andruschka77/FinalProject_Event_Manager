package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.dto.LocationDto;
import dev.sorokin.eventmanager.model.domain.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoMapper {

    public Location toDomain(LocationDto location) {
        return new Location(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCapacity(),
                location.getDescription()
        );
    }

    public LocationDto toDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCapacity(),
                location.getDescription()
        );
    }

}