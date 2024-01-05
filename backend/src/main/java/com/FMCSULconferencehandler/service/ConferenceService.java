package com.FMCSULconferencehandler.service;


import com.FMCSULconferencehandler.model.*;
import com.FMCSULconferencehandler.model.reqModel.LectureRequest;
import com.FMCSULconferencehandler.model.reqModel.SessionReq;
import com.FMCSULconferencehandler.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.FMCSULconferencehandler.controller.sha.Hashes.hashSHA512;

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
    private final AdminService adminService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceService.class);

    public ConferenceService(SessionRepository sessionRepository, ConferenceRepository conferenceRepository, LecturerRepository lecturerRepository, LectureRepository lectureRepository, EventRepository eventRepository, AttendeeRepository attendeeRepository, ParticipantRepository participantRepository, TypeRepository typeRepository, TitleRepository titleRepository,AdminService adminService) {
        this.sessionRepository = sessionRepository;
        this.eventRepository = eventRepository;
        this.attendeeRepository = attendeeRepository;
        this.participantRepository = participantRepository;
        this.lectureRepository=lectureRepository;
        this.lecturerRepository = lecturerRepository;
        this.conferenceRepository=conferenceRepository;
        this.typeRepository=typeRepository;
        this.titleRepository=titleRepository;
        this.adminService = adminService;
    }

    @Transactional
    public void addAllConference(Participant[] participants, SessionReq[] sessionReqs, Event[] eventsArr)
    {
        for(Participant participant:participants) {
            if (adminService.checkMail(participant.getEmail())) {
                //brak sprawdzania czy juz jest w bazie (email unique)
                participant.setPassword(adminService.passwordGenerate());
                participant.setPassword(hashSHA512(participant.getPassword()));
                participantRepository.save(participant);
            }
        }
        for(SessionReq session: sessionReqs) {
            Session newSession = new Session(session.getName(), session.getStartingDate(),
                    session.getEndingDate(), session.getCity(),
                    session.getStreet(), session.getBuilding(),
                    session.getRoom());
            sessionRepository.save(newSession);
            for(LectureRequest lecture: session.getEventsArr()) {
                Event event = new Event(lecture.getStartingDate(),lecture.getEndingDate()
                        ,lecture.getName(),newSession.getId());

//                if(!checkEvent(event))
//                    return ;

                eventRepository.save(event);

                if(!lecture.getLecturers().isEmpty()) {
                    Lecture lecture1 = new Lecture(lecture.getName(), lecture.get_abstract(), event);
                    List<String> emailSpeakers = lecture.getLecturers();
                    for (String email : emailSpeakers) {
                        if (participantRepository.findParticipantByEmail(email) == null)
                            return;
                    }
                    lectureRepository.save(lecture1);
                    for (String email : emailSpeakers) {
                        addSpeakerToLecture(lecture1.getId(),
                                participantRepository.findParticipantByEmail(email)
                                .orElseThrow(()->new EmptyResultDataAccessException("participant not found",1))
                                .getId());
                    }
                }
            }
        }
        if(eventsArr!=null) {
            for (Event event : eventsArr) {
                eventRepository.save(event);
            }
        }

    }

    public void addConference(Conference conference)
    {
        if(checkConference(conference)) {
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

    public void addAttendee(UUID event_ID, UUID participant_id)
    {
        Event event = eventRepository.findById(event_ID).orElseThrow(() -> new EmptyResultDataAccessException("Event not found", 1));
        Participant participant=participantRepository.findById(participant_id).orElseThrow(() -> new EmptyResultDataAccessException("participant not found", 1));

        event.setAmount_of_participants(event.getAmount_of_participants()+1);

        Attendee attendee = new Attendee(event,participant);

        eventRepository.save(event);
        attendeeRepository.save(attendee);
    }
    public List<Event> participantEvent(UUID id)
    {
        if (!participantRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Participant not found", 1);
        }
        return attendeeRepository.findEventByParticipantId(id);
    }

    public List<Event> eventsInSession(UUID id)
    {
        if (!sessionRepository.existsById(id)) {
            throw new EmptyResultDataAccessException("Session not found", 1);
        }
        return eventRepository.findBySessionFk(id);
    }
//    @Transactional
//    public Lecture addLecture(LectureRequest lecture)
//    {
//        Event event = new Event(lecture.getStartingDate(),lecture.getEndingDate()
//                ,lecture.getName(),lecture.getSession_fk());
//
//        if(sessionRepository.findSessionById(lecture.getSession_fk()) == null)
//            return null;
//
//        if(!checkEvent(event))
//            return null;
//
//        if(lecture.getTopic() == null || lecture.getTopic().isEmpty())
//            return null;
//
//        eventRepository.save(event);
//        if(!lecture.getLecturers().isEmpty()) {
//            Lecture lecture1 = new Lecture(lecture.getTopic(), lecture.getAbstract(), event);
//
//            List<String> emailSpeakers = lecture.getLecturers();
//            for (String email : emailSpeakers) {
//                if (participantRepository.findParticipantByEmail(email) == null)
//                    return null;
//            }
//
//            lectureRepository.save(lecture1);
//            for (String email : emailSpeakers) {
//                addSpeakerToLecture(lecture1.getId(), participantRepository.findParticipantByEmail(email).get(0).getId());
//            }
//
//            return lecture1;
//        }
//        return null;
//    }

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

    private boolean checkConference(Conference conference){
        if(conference.getName() == null || conference.getName().isEmpty())
            return false;
        if(conference.getDate_start().isAfter(conference.getDate_end()))
            return false;

        List<Conference> list = conferenceRepository.findAllByOrderByDate_start();

        for(int i = 0; i < list.size(); i++){
            if(!(conference.getDate_end().isBefore(list.get(i).getDate_start()) ||
                conference.getDate_start().isAfter(list.get(i).getDate_end())))
                return false;
        }
        return true;
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

        if(event.getParticipantFk() != null)
            if(participantRepository.findParticipantById(event.getParticipantFk()) == null)
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

    public void updateSession(Session session){
        Session updateSession = sessionRepository.findById(session.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Session not found" , 1));

        if(!checkSession(session))
            throw new CustomDataIntegrityViolationException("New data is invalid");

        updateSession.setName(session.getName());
        updateSession.setTime_start(session.getTime_start());
        updateSession.setTime_end(session.getTime_end());
        updateSession.setCity(session.getCity());
        updateSession.setStreet(session.getStreet());
        updateSession.setBuilding(session.getBuilding());
        updateSession.setRoom_number(session.getRoom_number());

        sessionRepository.save(updateSession);
    }

    public void updateType(Type type){
        Type updateType = typeRepository.findById(type.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Type not found", 1));

        if(type.getName() == null || type.getName().isEmpty())
            throw new CustomDataIntegrityViolationException("New data is invalid");

        updateType.setName(type.getName());
        updateType.setAbbreviation(type.getAbbreviation());

        typeRepository.save(updateType);

    }

    public void updateTitle(Title title){
        Title updateTitle = titleRepository.findById(title.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Title not found", 1));

        if((title.getName() == null || title.getName().isEmpty()) ||
                (title.getAbbreviation() == null || title.getAbbreviation().isEmpty()))
            throw new CustomDataIntegrityViolationException("New data is invalid");

        updateTitle.setName(title.getName());
        updateTitle.setAbbreviation(title.getAbbreviation());

        titleRepository.save(updateTitle);
    }

    public void updateLecture(Lecture lecture){
        Lecture updateLecture = lectureRepository.findById(lecture.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Lecture not found", 1));

        if(lecture.getTopic() == null || lecture.getTopic().isEmpty())
            throw new CustomDataIntegrityViolationException("New data is invalid");

        Event updateEvent = eventRepository.findById(lecture.getEvent().getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Event not found", 1));

        updateLecture.setTopic(lecture.getTopic());
        updateLecture.setAbstract(lecture.getAbstract());
        updateLecture.setEvent(updateEvent);

        lectureRepository.save(updateLecture);
    }

    public void updateEvent(Event event){
        Event updateEvent = eventRepository.findById(event.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Event not found", 1));

        if(!checkUpdateEvent(event))
            throw new CustomDataIntegrityViolationException("New data is invalid");

        updateEvent.setTime_start(event.getTime_start());
        updateEvent.setTime_end(event.getTime_end());
        updateEvent.setName(event.getName());
        updateEvent.setAmount_of_participants(event.getAmount_of_participants());
        updateEvent.setSessionFk(event.getSessionFk());
        updateEvent.setParticipantFk(event.getParticipantFk());

        eventRepository.save(updateEvent);
    }

    public void updateConference(Conference conference){
        Conference updateConference = conferenceRepository.findById(conference.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Conference not found", 1));

        if(!checkUpdateConf(conference))
            throw new CustomDataIntegrityViolationException("New data is invalid");

        updateConference.setName(conference.getName());
        updateConference.setDate_end(conference.getDate_end());
        updateConference.setDate_start(conference.getDate_start());

        conferenceRepository.save(updateConference);
    }

    private boolean checkUpdateEvent(Event event) {
        if(event.getTime_start() == null)
            return false;
        if(event.getTime_end() == null)
            return false;
        if(event.getName() == null || event.getName().isEmpty())
            return false;
        if(event.getTime_start().isAfter(event.getTime_end()))
            return false;

        if(event.getParticipantFk() != null)
            if(participantRepository.findParticipantById(event.getParticipantFk()) == null)
                return false;

        if(event.getSessionFk() != null){
            if(sessionRepository.findSessionById(event.getSessionFk()) == null)
                return false;

            List<Event> list = new ArrayList<>();

            for(Event e : eventRepository.findBySessionFkOrderByTime_startAsc(event.getSessionFk()))
                if(e.getSessionFk().equals(event.getSessionFk()) && e.getId() != event.getId())
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

    private boolean checkUpdateConf(Conference conference){
        if(conference.getName() == null || conference.getName().isEmpty())
            return false;
        if(conference.getDate_start().isAfter(conference.getDate_end()))
            return false;


        List<Conference> list = new ArrayList<>();

        for(Conference c : conferenceRepository.findAllByOrderByDate_start())
            if(c.getId() != conference.getId())
                list.add(c);

        for(int i = 0; i < list.size(); i++)
            if(!(conference.getDate_end().isBefore(list.get(i).getDate_start()) ||
                    conference.getDate_start().isAfter(list.get(i).getDate_end())))
                return false;
        return true;
    }
}