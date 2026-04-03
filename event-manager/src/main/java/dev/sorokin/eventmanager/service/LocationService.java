package dev.sorokin.eventmanager.service;

import dev.sorokin.eventmanager.exception.ResourceNotFoundException;
import dev.sorokin.eventmanager.mapper.LocationEntityConverter;
import dev.sorokin.eventmanager.model.domain.Location;
import dev.sorokin.eventmanager.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationEntityConverter entityConverter;

    public LocationService(
            LocationRepository locationRepository,
            LocationEntityConverter entityConverter
    ) {
        this.locationRepository = locationRepository;
        this.entityConverter = entityConverter;
    }

    public Location createLocation(Location locationToCreate) {
        if (locationRepository.existsByName(locationToCreate.getName())) {
            throw new IllegalArgumentException("Location name already taken");
        }

        var locationToSave = entityConverter.toEntity(locationToCreate);

        return entityConverter.toDomain(
                locationRepository.save(locationToSave)
        );
    }

    public List<Location> searchLocations(
            Integer pageNumber,
            Integer pageSize
    ) {
        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        return locationRepository.searchBooks(pageable)
                .stream()
                .map(entityConverter::toDomain)
                .toList();
    }

    public Location findById(Long locationId) {
        var foundLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location", locationId));

        return entityConverter.toDomain(foundLocation);
    }

    @Transactional
    public Location updateLocation(
            Long locationId,
            Location locationToUpdate
    ) {
        if (!locationRepository.existsById(locationId)) {
            throw new ResourceNotFoundException("Location", locationId);
        }

        locationRepository.updateLocation(
                locationId,
                locationToUpdate.getName(),
                locationToUpdate.getAddress(),
                locationToUpdate.getCapacity(),
                locationToUpdate.getDescription()
        );

        return entityConverter.toDomain(
                locationRepository.findById(locationId).orElseThrow()
        );
    }

    public void deleteLocation(Long locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new ResourceNotFoundException("Location", locationId);
        }

        locationRepository.deleteById(locationId);
    }

}