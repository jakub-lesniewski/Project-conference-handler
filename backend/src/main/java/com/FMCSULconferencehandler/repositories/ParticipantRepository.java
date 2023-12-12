package com.FMCSULconferencehandler.repositories;

import com.FMCSULconferencehandler.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    Participant findParticipantById(UUID id);
}
