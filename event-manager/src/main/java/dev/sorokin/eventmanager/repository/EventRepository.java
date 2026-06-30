package dev.sorokin.eventmanager.repository;

import dev.sorokin.eventmanager.model.entity.EventEntity;
import dev.sorokin.eventmanager.model.enums.EventStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    @EntityGraph(attributePaths = {"owner"})
    @Query("SELECT e FROM EventEntity e WHERE e.id = :eventId")
    Optional<EventEntity> findByIdWithOwner(@Param("eventId") Long eventId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM EventEntity e WHERE e.id = :eventId")
    Optional<EventEntity> findByIdWithLock(@Param("eventId") Long eventId);

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

    @EntityGraph(attributePaths = {"location", "owner"})
    @Query("""
        SELECT DISTINCT e FROM EventEntity e
        JOIN RegistrationEntity r ON r.event = e
        WHERE r.user.id = :userId
    """)
    Page<EventEntity> findEventsByUserId(@Param("userId") Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"owner"})
    @Query("""
    SELECT e FROM EventEntity e
    WHERE e.status = :oldStatus
        AND e.startAt <= :timeNow
    """)
    List<EventEntity> findEventsToStart(
            @Param("oldStatus") EventStatus oldStatus,
            @Param("timeNow") LocalDateTime time
    );

    @Query(value = """
    SELECT * FROM events
    WHERE status = :oldStatus
        AND start_at + (duration_minutes || ' minutes')::interval <= :timeNow
   """, nativeQuery = true)
    List<EventEntity> findEventsToFinish(
            @Param("oldStatus") String oldStatus,
            @Param("timeNow") LocalDateTime time
    );

}