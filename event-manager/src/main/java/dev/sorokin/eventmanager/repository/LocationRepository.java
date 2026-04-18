package dev.sorokin.eventmanager.repository;

import dev.sorokin.eventmanager.model.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    boolean existsByName(String name);

}