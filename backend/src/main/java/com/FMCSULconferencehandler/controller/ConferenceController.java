package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.conference.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class ConferenceController {
    @Autowired
    private ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @PostMapping("/addSession")
    public ResponseEntity<Session> addSession(@RequestBody Session session)
    {
        conferenceService.addSession(session);
        return new ResponseEntity<>(session, HttpStatus.CREATED);
    }

    @PostMapping("/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody Event event)
    {
        conferenceService.addEvent(event);

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PostMapping ("/ParticipantToEvent")
    public ResponseEntity<String> addParticipantToEvent(@RequestBody ParticipantToEventDTO dto)
    {
        conferenceService.addParticipantToEvent(dto.getIdEvent(),dto.getIdParticipant());
        return new ResponseEntity<>("PARTICIPANT ADDED", HttpStatus.OK);
    }


    @GetMapping("/attendance/{id}")
    public ResponseEntity<List<Event>> eventsForParticipants(@PathVariable("id") UUID id)
    {
        return new  ResponseEntity<>(conferenceService.participantEvent(id),HttpStatus.OK);
    }

    @GetMapping("/eventInSession/{id}")
    public ResponseEntity<List<Event>> eventInSession(@PathVariable("id") UUID id)
    {
        List<Event> eventList = conferenceService.eventsInSession(id);
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @PostMapping("/addLecture")
    public ResponseEntity<Lecture> addLecture(@RequestBody LectureRequest lecture) {

        Lecture lecture1=conferenceService.addLecture(lecture);
        return new ResponseEntity<>(lecture1, HttpStatus.CREATED);
    }

    @GetMapping("/getLecture/{id}")
    public ResponseEntity<HashMap<String, Object>> getLecture(@PathVariable("id") UUID id) {


        return new ResponseEntity<>(conferenceService.getJsonLecture(id), HttpStatus.CREATED);
    }

    @GetMapping("/getAllConference")
    public  HashMap<String,Object> getAllConference()
    {
       return conferenceService.getConference();
    }








}
