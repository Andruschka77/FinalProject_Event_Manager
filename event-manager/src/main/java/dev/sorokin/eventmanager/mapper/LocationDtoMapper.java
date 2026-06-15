package dev.sorokin.eventmanager.mapper;

import dev.sorokin.eventmanager.dto.request.LocationRequest;
import dev.sorokin.eventmanager.dto.response.LocationResponse;
import dev.sorokin.eventmanager.model.domain.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoMapper {

    public Location toDomain(LocationRequest locationRequest){
        return new Location(
                locationRequest.id(),
                locationRequest.name(),
                locationRequest.address(),
                locationRequest.capacity(),
                locationRequest.description()
        );
    }

    public LocationResponse toDto(Location location) {
        return new LocationResponse(
                location.getId(),
                location.getName(),
                location.getAddress(),
                location.getCapacity(),
                location.getDescription()
        );
    }

}