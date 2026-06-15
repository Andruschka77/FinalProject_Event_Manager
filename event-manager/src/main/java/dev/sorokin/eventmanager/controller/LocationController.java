package dev.sorokin.eventmanager.controller;

import dev.sorokin.eventmanager.dto.request.LocationRequest;
import dev.sorokin.eventmanager.dto.response.LocationResponse;
import dev.sorokin.eventmanager.mapper.LocationDtoMapper;
import dev.sorokin.eventmanager.model.domain.Location;
import dev.sorokin.eventmanager.service.LocationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/locations")
@Slf4j
public class LocationController {

    private final LocationService locationService;
    private final LocationDtoMapper dtoMapper;

    public LocationController(
            LocationService locationService,
            LocationDtoMapper dtoMapper
    ) {
        this.locationService = locationService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(
            @RequestBody @Valid LocationRequest locationToCreate
    ) {
        log.info("Get request for create location: location={}", locationToCreate);

        Location createdLocation = locationService.createLocation(
                dtoMapper.toDomain(locationToCreate)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dtoMapper.toDto(createdLocation));
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations(
            @RequestParam(name = "pageNum", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "2") @Min(2) @Max(20) Integer pageSize
    ) {
        log.info("Get request for getAllLocations");

        List<LocationResponse> locations = locationService.searchLocations(pageNumber, pageSize)
                .stream()
                .map(dtoMapper::toDto)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locations);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationResponse> findLocationById(
            @PathVariable("locationId") Long locationId
    ) {
        log.info("Get request for find location by id: locationId={}", locationId);

        var foundLocation = locationService.findById(locationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtoMapper.toDto(foundLocation));
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<LocationResponse> updateLocation(
            @PathVariable("locationId") Long locationId,
            @RequestBody @Valid LocationRequest locationToUpdate
    ) {
        log.info("Get request for update location: locationId={}, locationToUpdate={}", locationId, locationToUpdate);

        var updatedLocation = locationService.updateLocation(
                locationId,
                dtoMapper.toDomain(locationToUpdate)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dtoMapper.toDto(updatedLocation));
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<Void> deleteLocation(
            @PathVariable("locationId") Long locationId
    ) {
        log.info("Get request for delete location by id: locationId={}", locationId);

        locationService.deleteLocation(locationId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}