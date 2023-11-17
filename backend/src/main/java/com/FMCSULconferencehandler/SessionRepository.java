package com.FMCSULconferencehandler;

import com.FMCSULconferencehandler.model.conference.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session,UUID> {

}
