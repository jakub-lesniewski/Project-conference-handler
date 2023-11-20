package com.FMCSULconferencehandler.model.conference;

import java.util.UUID;

public class ParticipantToEventDTO {
    private UUID idEvent;
    private UUID idParticipant;

    public ParticipantToEventDTO(UUID idEvent, UUID idParticipant) {
        this.idEvent = idEvent;
        this.idParticipant = idParticipant;
    }

    public UUID getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(UUID idEvent) {
        this.idEvent = idEvent;
    }

    public UUID getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(UUID idParticipant) {
        this.idParticipant = idParticipant;
    }
}
