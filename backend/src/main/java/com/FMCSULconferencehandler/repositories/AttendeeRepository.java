package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Attendee;
import com.FMCSULconferencehandler.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AttendeeRepository extends JpaRepository<Attendee, UUID> {
    @Query("SELECT event FROM Attendee  WHERE participant.id = :idParticipant")
    List<Event> findByParticipantId(@Param("idParticipant") UUID idParticipant);
}
