package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Attendee;
import com.FMCSULconferencehandler.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendeeRepository extends JpaRepository<Attendee, UUID> {
    @Query("SELECT event FROM Attendee  WHERE participant.id = :idParticipant")
    List<Event> findEventByParticipantId(@Param("idParticipant") UUID idParticipant);

    @Query("SELECT a FROM Attendee a WHERE a.participant.id = :idParticipant")
    List<Attendee> findAttendeesByParticipantId(@Param("idParticipant") UUID idParticipant);

    @Query("SELECT a FROM Attendee a WHERE a.event.id = :idEvent")
    List<Attendee> findAttendeesByEventId(@Param("idEvent") UUID idEvent);

    @Query("SELECT a FROM Attendee a WHERE a.event.id = :idEvent AND a.participant.id = :idParticipant")
    Optional<Attendee> findAttendeeByEventAndParticipant(@Param("idEvent") UUID idEvent, @Param("idParticipant") UUID idParticipant);

    @Modifying
    Long deleteByParticipantId(UUID idParticipant);

    boolean existsByParticipantId(UUID id);

    @Modifying
    Long deleteByEventId(UUID idEvent);

    boolean existsByEventId(UUID id);
}
