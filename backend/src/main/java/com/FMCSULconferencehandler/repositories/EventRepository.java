package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e WHERE e.session_fk = :session_fk")
    List<Event> findBySession_fk(@Param("session_fk") UUID session_fk);


}
