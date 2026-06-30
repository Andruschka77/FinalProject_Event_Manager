package dev.sorokin.eventmanager.repository;

import dev.sorokin.eventmanager.model.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {

    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    @Query("""
    SELECT r FROM RegistrationEntity r
    JOIN FETCH r.event
    WHERE r.user.id = :userId AND r.event.id = :eventId
    """)
    Optional<RegistrationEntity> findByUserIdAndEventId(Long userId, Long eventId);

    @Query("""
    SELECT r.user.id FROM RegistrationEntity r
    WHERE r.event.id = :eventId
    """)
    List<Long> findSubscriberIdsByEventId(@Param("eventId") Long eventId);

}