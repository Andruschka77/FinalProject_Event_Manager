package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.model.domain.Location;
import dev.sorokin.eventmanager.model.entity.LocationEntity;
import org.springframework.stereotype.Component;

@Component
public class LocationEntityMapper {

    public Location toDomain(LocationEntity location) {
        return new Location(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCapacity(),
                location.getDescription()
        );
    }

    public LocationEntity toEntity(Location location) {
        return new LocationEntity(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCapacity(),
                location.getDescription()
        );
    }

}