package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.conference.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
