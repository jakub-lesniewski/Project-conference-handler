package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.conference.ConferenceService;
import com.FMCSULconferencehandler.model.conference.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
