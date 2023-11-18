package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.conference.ConferenceService;
import com.FMCSULconferencehandler.model.conference.Event;
import com.FMCSULconferencehandler.model.conference.ParticipantToEventDTO;
import com.FMCSULconferencehandler.model.conference.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping ("/ParticipantToEvent")
    public ResponseEntity<String> addParticipantToEvent(@RequestBody ParticipantToEventDTO dto)
    {
        conferenceService.addParticipantToEvent(dto.getIdEvent(),dto.getIdParticipant());
        return new ResponseEntity<>("PARTICIPANT ADDED", HttpStatus.OK);
    }

}
