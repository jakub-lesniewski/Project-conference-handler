package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.*;
import com.FMCSULconferencehandler.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @PostMapping ("/ParticipantToEvent")
    public ResponseEntity<Map> addParticipantToEvent(@RequestBody ParticipantToEventDTO dto)
    {
        Map<String, String> object = new HashMap<>();
        try{
            conferenceService.addParticipantToEvent(dto.getIdEvent(),dto.getIdParticipant());
        }catch (RuntimeException e) {
            object.put("error", e.getClass().getSimpleName());
            return new ResponseEntity<>(object, HttpStatus.FORBIDDEN);
        }
        object.put("success", "participant added");
        return new ResponseEntity<>(object, HttpStatus.OK);
    }


    @GetMapping("/attendance/{id}")
    public ResponseEntity<List<Event>> eventsForParticipants(@PathVariable("id") UUID id)
    {
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
    public ResponseEntity<HashMap<String, Object>> getLecture(@PathVariable("id") UUID id) {
        if(conferenceService.getJsonLecture(id).containsKey("error"))
            return new ResponseEntity<>(conferenceService.getJsonLecture(id), HttpStatus.CONFLICT);
        return new ResponseEntity<>(conferenceService.getJsonLecture(id), HttpStatus.CREATED);
    }

    @GetMapping("/getAllConference")
    public  HashMap<String,Object> getAllConference()
    {
       return conferenceService.getConference();
    }








}
