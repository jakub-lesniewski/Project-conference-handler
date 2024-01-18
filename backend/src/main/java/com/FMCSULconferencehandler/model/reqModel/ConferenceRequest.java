package com.FMCSULconferencehandler.model.reqModel;

import com.FMCSULconferencehandler.model.Event;
import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.model.Session;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ConferenceRequest {
    private ParticipantReq[] attendees;
    private SessionReq[] sessions;
    private EventReq[] events;
}

