package dev.sorokin.eventmanager.repository;

import dev.sorokin.eventmanager.model.entity.EventEntity;
import dev.sorokin.eventmanager.model.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @EntityGraph(attributePaths = {"location", "owner"})
    @Query("""
            SELECT e
            FROM EventEntity e
            WHERE e.owner.id = :ownerId
    """)
    Page<EventEntity> findEventsByOwnerId(@Param("ownerId") Long ownerId, Pageable pageable);

    @EntityGraph(attributePaths = {"location", "owner"})
    @Query("""
        SELECT e FROM EventEntity e
        WHERE e.name = :name
          AND e.maxPlaces BETWEEN :placesMin AND :placesMax
          AND e.startAt BETWEEN :dateStartAfter AND :dateStartBefore
          AND e.costTickets BETWEEN :costMin AND :costMax
          AND e.durationMinutes BETWEEN :durationMin AND :durationMax
          AND e.location.id = :locationId
          AND e.status = :eventStatus
    """)
    Page<EventEntity> findEventsByFilters(
            @Param("name") String name,
            @Param("placesMin") Integer placesMin,
            @Param("placesMax") Integer placesMax,
            @Param("dateStartAfter") LocalDateTime dateStartAfter,
            @Param("dateStartBefore") LocalDateTime dateStartBefore,
            @Param("costMin") Integer costMin,
            @Param("costMax") Integer costMax,
            @Param("durationMin") Integer durationMin,
            @Param("durationMax") Integer durationMax,
            @Param("locationId") Long locationId,
            @Param("eventStatus") EventStatus eventStatus,
            Pageable pageable
    );

}