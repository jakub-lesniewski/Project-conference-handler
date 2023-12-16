package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.*;
import com.FMCSULconferencehandler.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
public class ConferenceController {
    @Autowired
    private ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @PostMapping("/addConference")
    public ResponseEntity<Object> addConference(@RequestBody Conference conference)
    {

        try {
            conferenceService.addConference(conference);
        }
        catch (RuntimeException e)
        {
            Map<String,String> map=new HashMap<>();
            map.put("error","conference already exist");
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(conference, HttpStatus.CREATED);
    }

    @PostMapping("/addSession")
    public ResponseEntity<Object> addSession(@RequestBody Session session)
    {
        if(conferenceService.addSession(session))
            return new ResponseEntity<>(session, HttpStatus.CREATED);
        return new ResponseEntity<>("invalid data", HttpStatus.CONFLICT);
    }

    @PostMapping("/addEvent")
    public ResponseEntity<Object> addEvent(@RequestBody Event event)
    {
        if(conferenceService.addEvent(event))
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        return new ResponseEntity<>("invalid data", HttpStatus.CONFLICT);
    }

    @PostMapping ("/participantToEvent")
    public ResponseEntity<Map<String, String>> addParticipantToEvent(@RequestBody ParticipantToEventDTO dto)
    {
        Map<String, String> object = new HashMap<>();
        conferenceService.addParticipantToEvent(dto.getIdEvent(),dto.getIdParticipant());

        object.put("success", "participant added");
        return new ResponseEntity<>(object, HttpStatus.OK);
    }


    @GetMapping("/attendance/{id}")
    public ResponseEntity<Object> eventsForParticipants(@PathVariable("id") UUID id)
    {
        if(conferenceService.participantEvent(id) == null)
            return new ResponseEntity<>("user not found", HttpStatus.CONFLICT);
        return new  ResponseEntity<>(conferenceService.participantEvent(id),HttpStatus.OK);
    }

    @GetMapping("/eventInSession/{id}")
    public ResponseEntity<Object> eventInSession(@PathVariable("id") UUID id)//List<Event>
    {
        List<Event> eventList = conferenceService.eventsInSession(id);
        if(eventList != null)
            return new ResponseEntity<>(eventList, HttpStatus.OK);
        else
            return new ResponseEntity<>("no session", HttpStatus.CONFLICT);
    }

    @PostMapping("/addLecture")
    public ResponseEntity<Object> addLecture(@RequestBody LectureRequest lecture) {
        Lecture lecture1=conferenceService.addLecture(lecture);
        if(lecture1 == null)
            return new ResponseEntity<>("error", HttpStatus.CONFLICT);
        return new ResponseEntity<>(lecture1, HttpStatus.CREATED);
    }

    @GetMapping("/getLecture/{id}")
    public ResponseEntity<Map<String, Object>> getLecture(@PathVariable("id") UUID id) {
        if(conferenceService.getJsonLecture(id).containsKey("error"))
            return new ResponseEntity<>(conferenceService.getJsonLecture(id), HttpStatus.CONFLICT);
        return new ResponseEntity<>(conferenceService.getJsonLecture(id), HttpStatus.CREATED);
    }

    @GetMapping("/getAllConference")
    public  HashMap<String,Object> getAllConference()
    {
       return conferenceService.getConference();
    }

    @DeleteMapping("/deleteType")
    public ResponseEntity<Object> deleteType(@RequestParam("type") String id) {

        HashMap<String, Object> result = conferenceService.deleteType(id);
        return new ResponseEntity<>(result, (result.containsKey("error")) ? HttpStatus.CONFLICT : HttpStatus.OK);
    }

    @DeleteMapping("/deleteTitle")
    public ResponseEntity<Object> deleteTitle(@RequestParam("title") String id) {

        HashMap<String, Object> result = conferenceService.deleteTitle(id);
        return new ResponseEntity<>(result, (result.containsKey("error")) ? HttpStatus.CONFLICT : HttpStatus.OK);
    }

    @DeleteMapping("/deleteAttendee")
    public ResponseEntity<Object> deleteAttendee(@RequestParam("eventID") String eventID, @RequestParam("participantID") String participantID) {

        HashMap<String, Object> result = conferenceService.deleteAttendeeByEventAndParticipant(eventID, participantID);
        return new ResponseEntity<>(result, (result.containsKey("error")) ? HttpStatus.CONFLICT : HttpStatus.OK);
    }

    @DeleteMapping("/deleteAttendeesByParticipant")
    public ResponseEntity<Object> deleteAttendeesByParticipant(@RequestParam("participantID") String participantID) {

        HashMap<String, Object> result = conferenceService.deleteAttendeesByParticipant(participantID);
        return new ResponseEntity<>(result, (result.containsKey("error")) ? HttpStatus.CONFLICT : HttpStatus.OK);
    }

    @DeleteMapping("/deleteAttendeesByEvent")
    public ResponseEntity<Object> deleteAttendeesByEvent(@RequestParam("eventID") String eventID) {

        HashMap<String, Object> result = conferenceService.deleteAttendeesByEvent(eventID);
        return new ResponseEntity<>(result, (result.containsKey("error")) ? HttpStatus.CONFLICT : HttpStatus.OK);
    }

    @DeleteMapping("/deleteLecturer")
    public ResponseEntity<Object> deleteLecturer(@RequestParam("lectureID") String lectureID, @RequestParam("participantID") String participantID) {

        HashMap<String, Object> result = conferenceService.deleteLecturerByLectureAndParticipant(lectureID, participantID);
        return new ResponseEntity<>(result, (result.containsKey("error")) ? HttpStatus.CONFLICT : HttpStatus.OK);
    }

    @DeleteMapping("/deleteLecturersByParticipant")
    public ResponseEntity<Object> deleteLecturersByParticipant(@RequestParam("participantID") String participantID) {

        HashMap<String, Object> result = conferenceService.deleteLecturersByParticipant(participantID);
        return new ResponseEntity<>(result, (result.containsKey("error")) ? HttpStatus.CONFLICT : HttpStatus.OK);
    }

    @DeleteMapping("/deleteLecturersByLecture")
    public ResponseEntity<Object> deleteLecturersByLecture(@RequestParam("lectureID") String lectureID) {

        HashMap<String, Object> result = conferenceService.deleteLecturersByLecture(lectureID);
        return new ResponseEntity<>(result, (result.containsKey("error")) ? HttpStatus.CONFLICT : HttpStatus.OK);
    }

    @DeleteMapping("/deleteLecture")
    public ResponseEntity<Object> deleteLecture(@RequestParam("lectureID") String lectureID) {

        HashMap<String, Object> result = conferenceService.deleteLecturersByLecture(lectureID);
        return new ResponseEntity<>(result, (result.containsKey("error")) ? HttpStatus.CONFLICT : HttpStatus.OK);
    }
}
