package com.FMCSULconferencehandler.model.conference;


import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.repositories.Attendence_EventRepository;
import com.FMCSULconferencehandler.repositories.EventRepository;
import com.FMCSULconferencehandler.repositories.ParticipantRepository;
import com.FMCSULconferencehandler.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
       /* UUID sessionId = event.getSession().getId();
        Session session = sessionRepository.findById(sessionId).orElse(null);
        event.setSession(session);*/
        eventRepository.save(event);
    }

    public void addParticipantToEvent( UUID event_ID,UUID participant_id)
    {
        Event event = eventRepository.findById(event_ID).orElseThrow(() -> new RuntimeException("Event not found"));
        Participant participant=participantRepository.findById(participant_id).orElseThrow(() -> new RuntimeException("participant not found"));

        event.setAmount_of_participants(event.getAmount_of_participants()+1);

        Attendance_Event attendenceEvent=new Attendance_Event(event,participant);

        attendenceEventRepository.save(attendenceEvent);
    }
    public List<Event> participantEvent(UUID id)
    {
        List<Event> eventList = attendenceEventRepository.findByParticipantId(id);

        return  eventList;
    }

    public List<Event> eventsInSession(UUID id)
    {
        List<Event> eventList = eventRepository.findBySession_fk(id);

        return  eventList;
    }



}
