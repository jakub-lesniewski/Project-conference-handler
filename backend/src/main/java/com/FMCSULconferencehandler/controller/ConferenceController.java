package com.FMCSULconferencehandler.controller;

import com.FMCSULconferencehandler.model.*;
import com.FMCSULconferencehandler.model.reqModel.ConferenceRequest;
import com.FMCSULconferencehandler.model.reqModel.ParticipantToEventDTO;
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

    @PostMapping("/addAllConference")
    public ResponseEntity<JsonResponse> addAllConference(@RequestBody ConferenceRequest request) {
        conferenceService.addAllConference(request.getAtendeesArr(),request.getSessionsArr(),request.getEventsArr());
        return createSuccessJsonResponse("conference created");

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
            map.put("error","cannot add conference");
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
    public ResponseEntity<JsonResponse> addAttendee(@RequestBody ParticipantToEventDTO dto)
    {
        conferenceService.addAttendee(dto.getIdEvent(),dto.getIdParticipant());
        return createSuccessJsonResponse("participant added");
    }


    @GetMapping("/attendance/{id}")
    public ResponseEntity<Object> eventsForParticipants(@PathVariable("id") UUID id)
    {
        List<Event> events = conferenceService.participantEvent(id);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/eventInSession/{id}")
    public ResponseEntity<Object> eventInSession(@PathVariable("id") UUID id)//List<Event>
    {
        List<Event> eventList = conferenceService.eventsInSession(id);
        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

//    @PostMapping("/addLecture")
//    public ResponseEntity<Object> addLecture(@RequestBody LectureRequest lecture) {
//        Lecture lecture1=conferenceService.addLecture(lecture);
//        if(lecture1 == null)
//            return new ResponseEntity<>("error", HttpStatus.CONFLICT);
//        return new ResponseEntity<>(lecture1, HttpStatus.CREATED);
//    }

    @GetMapping("/getLecture/{id}")
    public ResponseEntity<Map<String, Object>> getLecture(@PathVariable("id") UUID id) {
        Map<String, Object> json = conferenceService.getJsonLecture(id);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/getAllConference")
    public  HashMap<String,Object> getAllConference()
    {
       return conferenceService.getConference();
    }

    @DeleteMapping("/deleteType")
    public ResponseEntity<JsonResponse> deleteType(@RequestParam("type") UUID id) {

        conferenceService.deleteType(id);
        return createSuccessJsonResponse("Type deleted");
    }

    @DeleteMapping("/deleteTitle")
    public ResponseEntity<JsonResponse> deleteTitle(@RequestParam("title") UUID id) {

        conferenceService.deleteTitle(id);
        return createSuccessJsonResponse("Title deleted");
    }

    @DeleteMapping("/deleteAttendee")
    public ResponseEntity<JsonResponse> deleteAttendee(@RequestParam("eventID") UUID eventID, @RequestParam("participantID") UUID participantID) {

        conferenceService.deleteAttendeeByEventAndParticipant(eventID, participantID);
        return createSuccessJsonResponse("Attendee deleted");
    }

    @DeleteMapping("/deleteAttendeesByParticipant")
    public ResponseEntity<JsonResponse> deleteAttendeesByParticipant(@RequestParam("participantID") UUID participantID) {

        Long numOfDeleted = conferenceService.deleteAttendeesByParticipant(participantID);
        return createSuccessJsonResponse("Deleted " + numOfDeleted + " attendee records");
    }

    @DeleteMapping("/deleteAttendeesByEvent")
    public ResponseEntity<JsonResponse> deleteAttendeesByEvent(@RequestParam("eventID") UUID eventID) {

        Long numOfDeleted = conferenceService.deleteAttendeesByEvent(eventID);
        return createSuccessJsonResponse("Deleted " + numOfDeleted + " attendee records");
    }

    @DeleteMapping("/deleteLecturer")
    public ResponseEntity<JsonResponse> deleteLecturer(@RequestParam("lectureID") UUID lectureID, @RequestParam("participantID") UUID participantID) {

        conferenceService.deleteLecturerByLectureAndParticipant(lectureID, participantID);
        return createSuccessJsonResponse("Lecturer deleted");
    }

    @DeleteMapping("/deleteLecturersByParticipant")
    public ResponseEntity<JsonResponse> deleteLecturersByParticipant(@RequestParam("participantID") UUID participantID) {

        Long numOfDeleted = conferenceService.deleteLecturersByParticipant(participantID);
        return createSuccessJsonResponse("Deleted " + numOfDeleted + " lecturer records");
    }

    @DeleteMapping("/deleteLecturersByLecture")
    public ResponseEntity<JsonResponse> deleteLecturersByLecture(@RequestParam("lectureID") UUID lectureID) {

        Long numOfDeleted = conferenceService.deleteLecturersByLecture(lectureID);
        return createSuccessJsonResponse("Deleted " + numOfDeleted + " lecturer records");
    }

    @DeleteMapping("/deleteLecture")
    public ResponseEntity<JsonResponse> deleteLecture(@RequestParam("lectureID") UUID lectureID) {

        conferenceService.deleteLecture(lectureID);
        return createSuccessJsonResponse("Lecture deleted");
    }

    @DeleteMapping("/deleteEvent")
    public ResponseEntity<JsonResponse> deleteEvent(@RequestParam("eventID") UUID eventID) {

        conferenceService.deleteEvent(eventID);
        return createSuccessJsonResponse("Event deleted");
    }

    @DeleteMapping("/deleteSession")
    public ResponseEntity<JsonResponse> deleteSession(@RequestParam("sessionID") UUID sessionID) {

        conferenceService.deleteSession(sessionID);
        return createSuccessJsonResponse("Session deleted");
    }

    @DeleteMapping("/deleteParticipant")
    public ResponseEntity<JsonResponse> deleteParticipant(@RequestParam("participantID") UUID participantID) {

        conferenceService.deleteParticipant(participantID);
        return createSuccessJsonResponse("Participant deleted");
    }

    @DeleteMapping("/deleteConference")
    public ResponseEntity<JsonResponse> deleteConference(@RequestParam("conferenceID") UUID conferenceID) {

        conferenceService.deleteConference(conferenceID);
        return createSuccessJsonResponse("Conference deleted");
    }

    @PutMapping("/updateSession")
    private ResponseEntity<JsonResponse> updateSession(@RequestBody Session session){
        conferenceService.updateSession(session);
        return createSuccessJsonResponse("Session updated");
    }

    @PutMapping("/updateType")
    private ResponseEntity<JsonResponse> updateType(@RequestBody Type type){
        conferenceService.updateType(type);
        return createSuccessJsonResponse("Type update");
    }

    @PutMapping("/updateTitle")
    private ResponseEntity<JsonResponse> updateTitle(@RequestBody Title title){
        conferenceService.updateTitle(title);
        return createSuccessJsonResponse("Title updated");
    }

    @PutMapping("/updateLecture")
    private ResponseEntity<JsonResponse> updateLecture(@RequestBody Lecture lecture){
        conferenceService.updateLecture(lecture);
        return createSuccessJsonResponse("Lecture updated");
    }

    @PutMapping("/updateEvent")
    private ResponseEntity<JsonResponse> updateEvent(@RequestBody Event event){
        conferenceService.updateEvent(event);
        return createSuccessJsonResponse("Event updated");
    }

    @PutMapping("/updateConference")
    private ResponseEntity<JsonResponse> updateConference(@RequestBody Conference conference){
        conferenceService.updateConference(conference);
        return createSuccessJsonResponse("Conference updated");
    }

    private ResponseEntity<JsonResponse> createSuccessJsonResponse(String message) {
        return new ResponseEntity<>(new JsonResponse(null, message), HttpStatus.OK);
    }
}
