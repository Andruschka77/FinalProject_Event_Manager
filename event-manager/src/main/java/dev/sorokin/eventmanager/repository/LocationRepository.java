package dev.sorokin.eventmanager.repository;

import dev.sorokin.eventmanager.model.entity.LocationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    boolean existsByName(String name);

    @Query("SELECT l from LocationEntity l")
    List<LocationEntity> searchBooks(
            Pageable pageable
    );

    @Modifying
    @Query("""
        UPDATE LocationEntity l
        SET l.name = :name,
            l.address = :address,
            l.capacity = :capacity,
            l.description = :description
        WHERE l.id = :id
    """)
    void updateLocation(
        @Param("id") Long id,
        @Param("name") String name,
        @Param("address") String address,
        @Param("capacity") Integer capacity,
        @Param("description") String description
    );

}