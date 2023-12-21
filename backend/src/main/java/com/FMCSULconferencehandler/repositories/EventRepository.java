package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Event e WHERE e.sessionFk = :sessionFk")
    List<Event> findBySessionFk(@Param("sessionFk") UUID sessionFk);

    @Query("select e from Event e where e.sessionFk = :sessionFk order by e.time_start asc")
    List<Event> findBySessionFkOrderByTime_startAsc(@Param("sessionFk") UUID sessionFk);

    boolean existsBySessionFk(UUID id);

    @Modifying
    void deleteBySessionFk(UUID id);

    boolean existsByParticipantFk(UUID id);
}
