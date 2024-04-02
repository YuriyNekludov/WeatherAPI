package edu.spring.weather_api.repository;

import edu.spring.weather_api.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    @Modifying
    @Query("delete from Session where expiresAt < CURRENT TIMESTAMP")
    void deleteExpiredSessions();
}
