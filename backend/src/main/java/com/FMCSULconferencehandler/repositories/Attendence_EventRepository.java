package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.conference.Attendance_Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface Attendence_EventRepository extends JpaRepository<Attendance_Event, UUID> {
}
