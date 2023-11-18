package com.FMCSULconferencehandler.model.conference;


import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.repositories.Attendence_EventRepository;
import com.FMCSULconferencehandler.repositories.EventRepository;
import com.FMCSULconferencehandler.repositories.ParticipantRepository;
import com.FMCSULconferencehandler.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ConferenceService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private Attendence_EventRepository attendenceEventRepository;

    @Autowired
    private ParticipantRepository participantRepository;


    public ConferenceService(SessionRepository sessionRepository, EventRepository eventRepository, Attendence_EventRepository attendenceEventRepository, ParticipantRepository participantRepository) {
        this.sessionRepository = sessionRepository;
        this.eventRepository = eventRepository;
        this.attendenceEventRepository = attendenceEventRepository;
        this.participantRepository = participantRepository;
    }

    public void addSession(Session session)
    {
        sessionRepository.save(session);
    }


    public void addEvent(Event event)
    {

        eventRepository.save(event);
    }

    public void addParticipantToEvent( UUID event_ID,UUID participant_id)
    {
        Event event = eventRepository.findById(event_ID).orElseThrow(() -> new RuntimeException("Event not found"));
        Participant participant=participantRepository.findById(participant_id).orElseThrow(() -> new RuntimeException("participant not found"));

        event.setAmount_of_participants(event.getAmount_of_participants()+1);

        Set<Attendance_Event> attendenceEvents = event.getEvent_fk();
        Attendance_Event attendenceEvent=new Attendance_Event(event,participant);
        attendenceEvents.add(attendenceEvent);

        eventRepository.save(event);



    }

}
