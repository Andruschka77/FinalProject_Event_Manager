package dev.sorokin.eventmanager.repository;

import dev.sorokin.eventmanager.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByLogin(String login);

    Optional<UserEntity> findByLogin(String login);

    @Query("""
    SELECT u.id FROM UserEntity u
    WHERE u.login = :login
    """)
    Optional<Long> findIdByLogin(@Param("login") String login);

}