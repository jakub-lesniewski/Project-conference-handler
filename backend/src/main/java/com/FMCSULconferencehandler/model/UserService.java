package com.FMCSULconferencehandler.model;

import com.FMCSULconferencehandler.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private ParticipantRepository participantRepository;
    private List<Participant> users=new LinkedList<>();
    public UserService(ParticipantRepository participantRepository)
    {
        this.participantRepository=participantRepository;
        users.addAll(participantRepository.findAll());

    }

    public void add(Participant user)
    {
        users.add(user);
        participantRepository.save(user);
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
}
//DO DOCUMENTATION
//JSON DO TABLICY