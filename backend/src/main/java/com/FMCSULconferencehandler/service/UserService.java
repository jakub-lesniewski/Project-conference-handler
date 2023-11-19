package com.FMCSULconferencehandler.service;

import com.FMCSULconferencehandler.ParticipantRepository;
import com.FMCSULconferencehandler.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EmailService emailService;
    private List<Participant> users=new LinkedList<>();
    public UserService(ParticipantRepository participantRepository)
    {
        this.participantRepository=participantRepository;
        users.addAll(participantRepository.findAll());

    }

    public void add(Participant... usersList)
    {
        for(Participant user:usersList)
        {
            users.add(user);
            participantRepository.save(user);
            emailService.sendAccountCreationMessage(user);
        }

    }
    public Participant getUserByEmail(String email)
    {
        for(Participant user:users)
        {
            if (user.getEmail_login().equals(email)) {
                return user;
            }

        }
        return null;
    }

    public  List<Participant> getParticipants()
    {
        return users;
    }
}
//DO DOCUMENTATION
//JSON DO TABLICY