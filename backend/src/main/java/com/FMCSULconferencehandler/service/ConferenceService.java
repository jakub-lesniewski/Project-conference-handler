package com.FMCSULconferencehandler.service;


import com.FMCSULconferencehandler.model.*;
import com.FMCSULconferencehandler.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.*;

@Service
public class ConferenceService {
    private SessionRepository sessionRepository;
    private EventRepository eventRepository;
    private AttendeeRepository attendenceEventRepository;
    private LecturerRepository attendenceLectureRepository;

    private ParticipantRepository participantRepository;
    private LectureRepository lectureRepository;
    private ConferenceRepository conferenceRepository;

    public ConferenceService(SessionRepository sessionRepository, ConferenceRepository conferenceRepository,LecturerRepository attendenceLectureRepository, LectureRepository lectureRepository, EventRepository eventRepository, AttendeeRepository attendenceEventRepository, ParticipantRepository participantRepository) {
        this.sessionRepository = sessionRepository;
        this.eventRepository = eventRepository;
        this.attendenceEventRepository = attendenceEventRepository;
        this.participantRepository = participantRepository;
        this.lectureRepository=lectureRepository;
        this.attendenceLectureRepository=attendenceLectureRepository;
        this.conferenceRepository=conferenceRepository;
    }

    public void addConference(Conference conference)
    {
        if(conferenceRepository.findAll().size()==0) {
            conferenceRepository.save(conference);
        }
        else
        {

            throw new RuntimeException();
        }
    }
    public boolean addSession(Session session)
    {
        if(checkSession(session)) {
            sessionRepository.save(session);
            return true;
        }
        return false;
    }

    public boolean addEvent(Event event)
    {
       /* UUID sessionId = event.getSession().getId();
        Session session = sessionRepository.findById(sessionId).orElse(null);
        event.setSession(session);*/
        if(checkEvent(event)) {
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    public void addParticipantToEvent( UUID event_ID,UUID participant_id)
    {
        Event event = eventRepository.findById(event_ID).orElseThrow(() -> new RuntimeException("Event not found"));
        Participant participant=participantRepository.findById(participant_id).orElseThrow(() -> new RuntimeException("participant not found"));

        event.setAmount_of_participants(event.getAmount_of_participants()+1);

        Attendee attendanceEvent=new Attendee(event,participant);
        eventRepository.save(event);

        attendenceEventRepository.save(attendanceEvent);
    }
    public List<Event> participantEvent(UUID id)
    {
        if(participantRepository.findParticipantById(id) == null)
            return null;
        List<Event> eventList = attendenceEventRepository.findByParticipantId(id);

        return  eventList;
    }

    public List<Event> eventsInSession(UUID id)
    {
        if(sessionRepository.findSessionById(id) == null)
            return null;
        List<Event> eventList = eventRepository.findBySession_fk(id);
        return  eventList;
    }
    @Transactional
    public Lecture addLecture(LectureRequest lecture)
    {

        Event event = new Event(lecture.getTime_start(),lecture.getTime_end()
                ,lecture.getName(),lecture.getSession_fk());

        if(sessionRepository.findSessionById(lecture.getSession_fk()) == null)
            return null;

        if(!checkEvent(event))
            return null;

        if(lecture.getTopic() == null || lecture.getTopic().isEmpty())
            return null;

        Lecture lecture1=new Lecture(lecture.getTopic(),lecture.getAbstract(),event);

        List<UUID> idSpeakers=lecture.getIdSpeakers();
        for(UUID id :idSpeakers) {
            if(participantRepository.findParticipantById(id) == null)
                return null;
        }

        eventRepository.save(event);
        lectureRepository.save(lecture1);
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
        //HashMap<String,Object> json=getLectureById(id).jsonLong();

        HashMap<String, Object> json = new HashMap<>();
        try {
            json = getLectureById(id).jsonLong();
        }catch(RuntimeException e){
            json.put("error", e.getMessage());
            return json;
        }

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

            Lecturer attendenceLecture=new Lecturer(lecture,participant);

            attendenceLectureRepository.save(attendenceLecture);
        }

    }
    public  HashMap<String,Object> getConference()
    {

        HashMap<String, Object> json = new HashMap<>();
        json.put("CONFERENCE",conferenceRepository.findAll());

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

    private boolean checkSession(Session session){
        if(session.getName() == null || session.getName().isEmpty())
            return false;
        if(session.getTime_start() == null)
            return false;
        if(session.getTime_end() == null)
            return false;
        if(session.getTime_start().isAfter(session.getTime_end()))
            return false;
        return true;
    }

    private boolean checkEvent(Event event){
        if(event.getTime_start() == null)
            return false;
        if(event.getTime_end() == null)
            return false;
        if(event.getName() == null || event.getName().isEmpty())
            return false;
        if(event.getTime_start().isAfter(event.getTime_end()))
            return false;

        if(event.getSession_fk() != null){
            if(sessionRepository.findSessionById(event.getSession_fk()) == null)
                return false;

            List<Event> list = new ArrayList<>();

            for(Event e : eventRepository.findBySession_fkOrderByTime_startAsc(event.getSession_fk()))
                if(e.getSession_fk().equals(event.getSession_fk()))
                    list.add(e);

            if(list.size() == 0 && !sessionRepository.findSessionById(event.getSession_fk()).getTime_start().isAfter(event.getTime_start())
                    && !sessionRepository.findSessionById(event.getSession_fk()).getTime_end().isBefore(event.getTime_end()))
                return true;
            if(!list.get(0).getTime_start().isBefore(event.getTime_end()) &&
                    !sessionRepository.findSessionById(
                            event.getSession_fk())
                            .getTime_start()
                            .isAfter(event.getTime_start()))
                return true;
            if(!(list.get(list.size() - 1).getTime_end().isAfter(event.getTime_start()))
            && !sessionRepository.findSessionById(event.getSession_fk()).getTime_end().isBefore(event.getTime_end()))
                return true;
            for(int i = 0; i < list.size(); i++){
                if(!list.get(i).getTime_end().isAfter(event.getTime_start()) &&
                        !list.get(i).getTime_start().isBefore(event.getTime_end()))
                    return true;
            }
            return false;
        }
        return true;
    }
}
