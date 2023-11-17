package com.FMCSULconferencehandler.model.conference;


import com.FMCSULconferencehandler.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {
    @Autowired
    private SessionRepository sessionRepository;

    public ConferenceService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void addSession(Session session)
    {
        sessionRepository.save(session);
    }
}
