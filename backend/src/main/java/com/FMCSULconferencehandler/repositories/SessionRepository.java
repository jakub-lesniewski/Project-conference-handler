package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session,UUID> {

}
