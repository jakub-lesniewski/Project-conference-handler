package com.FMCSULconferencehandler.service;


import com.FMCSULconferencehandler.model.*;
import com.FMCSULconferencehandler.repositories.*;
import jakarta.mail.Part;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConferenceService {
    private SessionRepository sessionRepository;
    private EventRepository eventRepository;
    private AttendeeRepository attendeeRepository;
    private LecturerRepository lecturerRepository;

    private ParticipantRepository participantRepository;
    private LectureRepository lectureRepository;
    private ConferenceRepository conferenceRepository;
    private TypeRepository typeRepository;
    private TitleRepository titleRepository;
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
        List<Event> eventList = attendeeRepository.findEventByParticipantId(id);

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

    // ========== DELETING SECTION ==================

    private UUID convertStringID (String idString) {    // Checks if given ID is correct
        try {
            return UUID.fromString(idString);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public HashMap<String, Object> deleteType (String idString) {

        HashMap<String, Object> json = new HashMap<>();

        UUID id = convertStringID(idString);
        if (id == null) {
            json.put("error", "invalid UUID format for typeID: " + idString);
            return json;
            //return "Invalid UUID format for typeID: " + idString;
        }
        Optional<Type> type = typeRepository.findById(id);
        if (type.isEmpty()) {
            json.put("error", "type not found");
            //return "Type not found";
        } else {
            typeRepository.deleteById(id);
            json.put("success", "type deleted");
            //return "Type deleted";
        }

        return json;
    }

    public HashMap<String, Object> deleteTitle(String idString) {

        HashMap<String, Object> json = new HashMap<>();
        UUID id = convertStringID(idString);

        if (id == null) {
            json.put("error", "Invalid UUID format for titleID: " + idString);
        } else {
            Optional<Title> title = titleRepository.findById(id);
            if (title.isEmpty()) {
                json.put("error", "Title not found");
            } else {
                titleRepository.deleteById(id);
                json.put("success", "Title deleted");
            }
        }

        return json;
    }

    public HashMap<String, Object> deleteAttendeeByEventAndParticipant(String idEvent, String idParticipant) {
        HashMap<String, Object> json = new HashMap<>();

        // Convert String IDs to UUID
        UUID eventID = convertStringID(idEvent);
        UUID participantID = convertStringID(idParticipant);

        // Check ID validity
        if (eventID == null) {
            json.put("error", "Invalid UUID format for eventID: " + idEvent);
        } else if (participantID == null) {
            json.put("error", "Invalid UUID format for participantID: " + idParticipant);
        } else {
            // Find a record in the database
            Optional<Attendee> attendee = attendeeRepository.findAttendeeByEventAndParticipant(eventID, participantID);

            if (attendee.isEmpty()) {
                json.put("error", "Attendee not found");
            } else {
                UUID id = attendee.get().getId();
                attendeeRepository.deleteById(id);
                json.put("success", "Attendee deleted");
            }
        }

        return json;
    }


    public HashMap<String, Object> deleteAttendeesByParticipant(String idString) {

        HashMap<String, Object> json = new HashMap<>();
        UUID id = convertStringID(idString);

        if (id == null) {
            json.put("error", "Invalid UUID format for participantID: " + idString);
        } else {
            List<Attendee> attendees = attendeeRepository.findAttendeesByParticipantId(id);
            if (attendees.isEmpty()) {
                json.put("info", "No attendee records found for this participant");
            } else {
                for (Attendee attendee : attendees) {
                    attendeeRepository.deleteById(attendee.getId());
                }
                json.put("success", "Attendee records for participantID: " + idString + " deleted");
            }
        }

        return json;
    }

    public HashMap<String, Object> deleteAttendeesByEvent(String idString) {
        HashMap<String, Object> json = new HashMap<>();

        UUID id = convertStringID(idString);
        if (id == null) {
            json.put("error", "Invalid UUID format for eventID: " + idString);
        } else {
            List<Attendee> attendees = attendeeRepository.findAttendeesByEventId(id);

            if (attendees.isEmpty()) {
                json.put("info", "No attendee records found for this event");
            } else {
                for (Attendee attendee : attendees) {
                    attendeeRepository.deleteById(attendee.getId());
                }
                json.put("success", "Attendee records for eventID: " + idString + " deleted");
            }
        }

        return json;
    }

    public HashMap<String, Object> deleteLecturerByLectureAndParticipant(String idLectureString, String idParticipantString) {
        HashMap<String, Object> json = new HashMap<>();

        // Convert String IDs to UUID
        UUID idLecture = convertStringID(idLectureString);
        UUID idParticipant = convertStringID(idParticipantString);

        // Check ID validity
        if (idLecture == null) {
            json.put("error", "Invalid UUID format for lectureID: " + idLectureString);
        } else if (idParticipant == null) {
            json.put("error", "Invalid UUID format for participantID: " + idParticipantString);
        } else {
            // Find a record in the database
            Optional<Lecturer> lecturer = lecturerRepository.findLecturerByLectureAndParticipant(idLecture, idParticipant);

            if (lecturer.isEmpty()) {
                json.put("error", "Lecturer not found");
            } else {
                UUID id = lecturer.get().getId();
                lecturerRepository.deleteById(id);
                json.put("success", "Lecturer deleted");
            }
        }

        return json;
    }

    public HashMap<String, Object> deleteLecturersByParticipant(String idString) {
        HashMap<String, Object> json = new HashMap<>();

        UUID id = convertStringID(idString);
        if (id == null) {
            json.put("error", "Invalid UUID format for participantID: " + idString);
        } else {
            List<Lecturer> lecturers = lecturerRepository.findLecturersByParticipant(id);

            if (lecturers.isEmpty()) {
                json.put("info", "No attendee records found for this participant");
            } else {
                for (Lecturer lecturer : lecturers) {
                    lecturerRepository.deleteById(lecturer.getId());
                }
                json.put("success", "Lecturer records for participantID: " + idString + " deleted");
            }
        }

        return json;
    }

    public HashMap<String, Object> deleteLecturersByLecture(String idString) {
        HashMap<String, Object> json = new HashMap<>();

        UUID id = convertStringID(idString);
        if (id == null) {
            json.put("error", "Invalid UUID format for lectureID: " + idString);
        } else {
            List<Lecturer> lecturers = lecturerRepository.findLecturersByLecture(id);

            if (lecturers.isEmpty()) {
                json.put("info", "No attendee records found for this participant");
            } else {
                for (Lecturer lecturer : lecturers) {
                    lecturerRepository.deleteById(lecturer.getId());
                }
                json.put("success", "Lecturer records for lectureID: " + idString + " deleted");
            }
        }

        return json;
    }

    public HashMap<String, Object> deleteLecture(String idString) {
        HashMap<String, Object> json = new HashMap<>();

        UUID id = convertStringID(idString);
        if (id == null) {
            json.put("error", "Invalid UUID format for lectureID: " + idString);
        } else {
            Optional<Lecture> lecture = lectureRepository.findById(id);
            if (lecture.isEmpty()) {
                json.put("error", "Lecture not found");
            } else {
                deleteLecturersByLecture(idString);
                lectureRepository.deleteById(id);
                json.put("success", "Lecture deleted");
            }
        }

        return json;
    }
}