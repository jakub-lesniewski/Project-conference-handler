package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.conference.Attendance_Event;
import com.FMCSULconferencehandler.model.conference.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface Attendence_EventRepository extends JpaRepository<Attendance_Event, UUID> {
    @Query("SELECT event FROM Attendance_Event  WHERE participant.id = :idParticipant")
    List<Event> findByParticipantId(@Param("idParticipant") UUID idParticipant);
}
