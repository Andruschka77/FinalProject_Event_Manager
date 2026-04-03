package dev.sorokin.eventmanager.controller;

import dev.sorokin.eventmanager.dto.LocationDto;
import dev.sorokin.eventmanager.mapper.LocationDtoConverter;
import dev.sorokin.eventmanager.model.domain.Location;
import dev.sorokin.eventmanager.service.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    private final LocationService locationService;
    private final LocationDtoConverter dtoConverter;

    public LocationController(
            LocationService locationService,
            LocationDtoConverter dtoConverter
    ) {
        this.locationService = locationService;
        this.dtoConverter = dtoConverter;
    }

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(
            @RequestBody @Valid LocationDto locationToCreate
    ) {
        log.info("Get request for create location: location={}", locationToCreate);

        Location createdLocation = locationService.createLocation(
                dtoConverter.toDomain(locationToCreate)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dtoConverter.toDto(createdLocation));
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations(
            @RequestParam(name = "pageNum", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "2") @Min(2) @Max(20) Integer pageSize
    ) {
        log.info("Get request for getAllLocations");

        List<LocationDto> locations = locationService.searchLocations(pageNumber, pageSize)
                .stream()
                .map(dtoConverter::toDto)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locations);

    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDto> findLocationById(
            @PathVariable("locationId") Long locationId
    ) {
        log.info("Get request for find location by id: locationId={}", locationId);

        var foundLocation = locationService.findById(locationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtoConverter.toDto(foundLocation));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<LocationDto> updateLocation(
            @PathVariable("locationId") Long locationId,
            @RequestBody @Valid LocationDto locationToUpdate
    ) {
        log.info("Get request for update location: locationId={}, locationToUpdate={}", locationId, locationToUpdate);

        var updatedLocation = locationService.updateLocation(
                locationId,
                dtoConverter.toDomain(locationToUpdate)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtoConverter.toDto(updatedLocation));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> deleteLocation(
            @PathVariable("locationId") Long locationId
    ) {
        log.info("Delete request for delete location by id: locationId={}", locationId);

        locationService.deleteLocation(locationId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}