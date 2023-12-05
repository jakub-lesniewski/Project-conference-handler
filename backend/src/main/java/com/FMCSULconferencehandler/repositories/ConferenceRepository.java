package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConferenceRepository extends JpaRepository<Conference,UUID> {
}
