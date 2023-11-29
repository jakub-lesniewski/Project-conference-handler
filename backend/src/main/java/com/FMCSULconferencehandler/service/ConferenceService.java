package com.FMCSULconferencehandler.service;


import com.FMCSULconferencehandler.model.*;
import com.FMCSULconferencehandler.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConferenceService {
    private SessionRepository sessionRepository;
    private EventRepository eventRepository;
    private AttendeeRepository attendenceEventRepository;
    private LecteurerRepository attendenceLectureRepository;

    private ParticipantRepository participantRepository;
    private LectureRepository lectureRepository;

    public ConferenceService(SessionRepository sessionRepository, LecteurerRepository attendenceLectureRepository, LectureRepository lectureRepository, EventRepository eventRepository, AttendeeRepository attendenceEventRepository, ParticipantRepository participantRepository) {
        this.sessionRepository = sessionRepository;
        this.eventRepository = eventRepository;
        this.attendenceEventRepository = attendenceEventRepository;
        this.participantRepository = participantRepository;
        this.lectureRepository=lectureRepository;
        this.attendenceLectureRepository=attendenceLectureRepository;
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

        Attendee attendanceEvent=new Attendee(event,participant);

        attendenceEventRepository.save(attendanceEvent);
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
    @Transactional
    public Lecture addLecture(LectureRequest lecture)
    {
        Event event = new Event(lecture.getTime_start(),lecture.getTime_end()
                ,lecture.getName(),lecture.getSession_fk());


        eventRepository.save(event);
        Lecture lecture1=new Lecture(lecture.getTopic(),lecture.getAbstract(),event);

        lectureRepository.save(lecture1);

        List<UUID> idSpeakers=lecture.getIdSpeakers();
        for(UUID id :idSpeakers)
        {
            addSpeakerToLecture(lecture1.getId(),id);
        }

        return lecture1;
    }


    public Lecture getLectureById(UUID id) {
        return lectureRepository.findById(id).orElseThrow(() -> new RuntimeException("lecture not found"));

    }
    public HashMap<String,Object>getJsonLecture(UUID id)
    {
        HashMap<String,Object> json=getLectureById(id).jsonLong();
        List<String> speakerIds = new ArrayList<>();
        for(Participant p : attendenceLectureRepository.findByLectureId(id))
        {
            speakerIds.add(p.getId().toString());
        }
        json.put("speaker",speakerIds);
        return json;
    }

    private void addSpeakerToLecture(UUID event_ID, UUID... participant_id)
    {
        Lecture lecture = lectureRepository.findById(event_ID).orElseThrow(() -> new RuntimeException("Lecture not found"));

        for(UUID participant_id2:participant_id)
        {
            Participant participant=participantRepository.findById(participant_id2).orElseThrow(() -> new RuntimeException("participant not found"));
            lecture.getEvent().setAmount_of_participants(lecture.getEvent().getAmount_of_participants()+1);

            Lecteurer attendenceLecture=new Lecteurer(lecture,participant);

            attendenceLectureRepository.save(attendenceLecture);
        }

    }
    public  HashMap<String,Object> getConference()
    {
        HashMap<String, Object> json = new HashMap<>();
        for (Session session : sessionRepository.findAll()) {
            json.put("SESSION", session);
            List<Event> events = new ArrayList<>();
            List<Map<String, Object>> lecturesData = new ArrayList<>();

            for (Event e : eventRepository.findBySession_fk(session.getId())) {
                for (Lecture l : lectureRepository.findByEvent_fk(e.getId())) {
                    lecturesData.add(getJsonLecture(l.getId()));
                }
                events.add(e);
            }
            json.put("EVENT", events);
            json.put("LECTURE", lecturesData);
        }
        return json;
    }
}
