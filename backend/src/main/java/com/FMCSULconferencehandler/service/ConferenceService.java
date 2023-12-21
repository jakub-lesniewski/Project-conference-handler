package com.FMCSULconferencehandler.service;


import com.FMCSULconferencehandler.model.*;
import com.FMCSULconferencehandler.repositories.*;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ConferenceService {
    private final SessionRepository sessionRepository;
    private final EventRepository eventRepository;
    private final AttendeeRepository attendeeRepository;
    private final LecturerRepository lecturerRepository;
    private final ParticipantRepository participantRepository;
    private final LectureRepository lectureRepository;
    private final ConferenceRepository conferenceRepository;
    private final TypeRepository typeRepository;
    private final TitleRepository titleRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceService.class);

    public ConferenceService(SessionRepository sessionRepository, ConferenceRepository conferenceRepository, LecturerRepository lecturerRepository, LectureRepository lectureRepository, EventRepository eventRepository, AttendeeRepository attendeeRepository, ParticipantRepository participantRepository, TypeRepository typeRepository, TitleRepository titleRepository) {
        this.sessionRepository = sessionRepository;
        this.eventRepository = eventRepository;
        this.attendeeRepository = attendeeRepository;
        this.participantRepository = participantRepository;
        this.lectureRepository=lectureRepository;
        this.lecturerRepository = lecturerRepository;
        this.conferenceRepository=conferenceRepository;
        this.typeRepository=typeRepository;
        this.titleRepository=titleRepository;
    }

    public void addConference(Conference conference)
    {
        if(conferenceRepository.findAll().isEmpty()) {
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
        Event event = eventRepository.findById(event_ID).orElseThrow(() -> new EmptyResultDataAccessException("Event not found", 1));
        Participant participant=participantRepository.findById(participant_id).orElseThrow(() -> new EmptyResultDataAccessException("participant not found", 1));

        event.setAmount_of_participants(event.getAmount_of_participants()+1);

        Attendee attendanceEvent=new Attendee(event,participant);
        eventRepository.save(event);

        attendeeRepository.save(attendanceEvent);
    }
    public List<Event> participantEvent(UUID id)
    {
        if(participantRepository.findParticipantById(id) == null)
            return null;

        return attendeeRepository.findEventByParticipantId(id);
    }

    public List<Event> eventsInSession(UUID id)
    {
        if(sessionRepository.findSessionById(id) == null)
            return null;
        return eventRepository.findBySessionFk(id);
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

    public Map<String,Object>getJsonLecture(UUID id)
    {
        Map<String, Object> json = lectureRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("lecture not found", 1))
                .jsonLong();

        List<String> speakerIds = lecturerRepository.findByLectureId(id).stream()
                .map(Participant::getId)
                .map(UUID::toString)
                .toList();

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

            Lecturer lecturer=new Lecturer(lecture,participant);

            lecturerRepository.save(lecturer);
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

            for (Event e : eventRepository.findBySessionFk(session.getId())) {
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

        if(event.getSessionFk() != null){
            if(sessionRepository.findSessionById(event.getSessionFk()) == null)
                return false;

            List<Event> list = new ArrayList<>();

            for(Event e : eventRepository.findBySessionFkOrderByTime_startAsc(event.getSessionFk()))
                if(e.getSessionFk().equals(event.getSessionFk()))
                    list.add(e);

            if(list.isEmpty() && !sessionRepository.findSessionById(event.getSessionFk()).getTime_start().isAfter(event.getTime_start())
                    && !sessionRepository.findSessionById(event.getSessionFk()).getTime_end().isBefore(event.getTime_end()))
                return true;
            if(!list.get(0).getTime_start().isBefore(event.getTime_end()) &&
                    !sessionRepository.findSessionById(
                            event.getSessionFk())
                            .getTime_start()
                            .isAfter(event.getTime_start()))
                return true;
            if(!(list.get(list.size() - 1).getTime_end().isAfter(event.getTime_start()))
            && !sessionRepository.findSessionById(event.getSessionFk()).getTime_end().isBefore(event.getTime_end()))
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

    public void deleteType (UUID id) {

        if(!typeRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Type not found", 1);
        }

        try {
            typeRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Could not delete type, data integrity violated");
        }
    }

    public void deleteTitle(UUID id) {

        if (!titleRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Title not found", 1);
        }

        try {
            titleRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Could not delete title, data integrity violated");
        }
    }

    public void deleteAttendeeByEventAndParticipant(UUID idEvent, UUID idParticipant) {

        Attendee attendee = attendeeRepository.findAttendeeByEventAndParticipant(idEvent, idParticipant).
                orElseThrow(() -> new EmptyResultDataAccessException("Attendee not found", 1));

        attendeeRepository.deleteById(attendee.getId());
    }

    @Transactional
    public Long deleteAttendeesByParticipant(UUID id) {

        if(!participantRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Participant not found", 1);
        }
        Long numOfDeleted = 0L;

        if (attendeeRepository.existsByParticipantId(id)) {
            numOfDeleted = attendeeRepository.deleteByParticipantId(id);
        }
        return numOfDeleted;
    }

    @Transactional
    public Long deleteAttendeesByEvent(UUID id) {

        if(!eventRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Event not found", 1);
        }
        Long numOfDeleted = 0L;

        if (attendeeRepository.existsByEventId(id)) {
            numOfDeleted = attendeeRepository.deleteByEventId(id);
        }
        return numOfDeleted;
    }

    public void deleteLecturerByLectureAndParticipant(UUID idLecture, UUID idParticipant) {

        Lecturer lecturer = lecturerRepository.findLecturerByLectureAndParticipant(idLecture, idParticipant).
                orElseThrow(() -> new EmptyResultDataAccessException("Lecturer not found", 1));

        lecturerRepository.deleteById(lecturer.getId());
    }

    @Transactional
    public Long deleteLecturersByParticipant(UUID id) {

        if(!participantRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Participant not found", 1);
        }
        Long numOfDeleted = 0L;

        if (lecturerRepository.existsByParticipantId(id)) {
            numOfDeleted = lecturerRepository.deleteByParticipantId(id);
        }
        return numOfDeleted;
    }

    @Transactional
    public Long deleteLecturersByLecture(UUID id) {

        if(!lectureRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Lecture not found", 1);
        }
        Long numOfDeleted = 0L;

        if (lecturerRepository.existsByLectureId(id)) {
            numOfDeleted = lecturerRepository.deleteByLectureId(id);
        }
        return numOfDeleted;
    }

    @Transactional
    public void deleteLecture(UUID id) {

        try {
            if (!lectureRepository.existsById(id)){
                throw new EmptyResultDataAccessException("Lecture not found", 1);
            }

            if (lecturerRepository.existsByLectureId(id)) {
                lecturerRepository.deleteByLectureId(id);
            }

            lectureRepository.deleteById(id);
            lectureRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Could not delete lecture, data integrity violated", ex);
        }
    }

    @Transactional
    public void deleteEvent(UUID id) {

        if (!eventRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Event not found", 1);
        }

        try {
            if (lectureRepository.existsByEventId(id)) {
                Lecture lecture = lectureRepository.findByEventId(id).
                        orElseThrow(() -> new EmptyResultDataAccessException("Lecture not found", 1));
                if (lecturerRepository.existsByLectureId(lecture.getId())) {
                    lecturerRepository.deleteByLectureId(lecture.getId());
                }

                lectureRepository.deleteByEventId(id);
            }
            if (attendeeRepository.existsByEventId(id)) {
                attendeeRepository.deleteByEventId(id);
            }

            eventRepository.deleteById(id);
            eventRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Could not delete event, data integrity violated", ex);
        }
    }

    @Transactional
    public void deleteSession(UUID id) {

        if (!sessionRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Session not found", 1);
        }

        try {
            if(eventRepository.existsBySessionFk(id)) {
                List<Event> events = eventRepository.findBySessionFk(id);

                for(Event event: events) {
                    if (lectureRepository.existsByEventId(event.getId())) {
                        Lecture lecture = lectureRepository.findByEventId(event.getId()).
                                orElseThrow(() -> new EmptyResultDataAccessException("Lecture not found", 1));
                        if (lecturerRepository.existsByLectureId(lecture.getId())) {
                            lecturerRepository.deleteByLectureId(lecture.getId());
                        }
                        lectureRepository.deleteByEventId(event.getId());
                    }
                    if (attendeeRepository.existsByEventId(event.getId())) {
                        attendeeRepository.deleteByEventId(event.getId());
                    }
                }
                eventRepository.deleteBySessionFk(id);
            }

            sessionRepository.deleteById(id);
            sessionRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Could not delete session, data integrity violated", ex);
        }
    }

    @Transactional
    public void deleteParticipant(UUID id) {

        if (!participantRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Participant not found", 1);
        }

        try {
            if(lecturerRepository.existsByParticipantId(id)) {
                lecturerRepository.deleteByParticipantId(id);
            }
            if(attendeeRepository.existsByParticipantId(id)) {
                attendeeRepository.deleteByParticipantId(id);
            }
            if(eventRepository.existsByParticipantFk(id)) {
                throw new CustomDataIntegrityViolationException("Cannot delete a participant that is a chairman in an event");
            }

            participantRepository.deleteById(id);
            participantRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new CustomDataIntegrityViolationException("Could not delete participant, data integrity violated", ex);
        }
    }

    public void deleteConference (UUID id) {

        if (!conferenceRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Conference not found", 1);
        }

        conferenceRepository.deleteById(id);
    }
}