package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Event;
import com.FMCSULconferencehandler.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    Participant findParticipantById(UUID id);
    Optional<Participant> findParticipantByEmail(String email);

    boolean existsByEmail(String email);
}
