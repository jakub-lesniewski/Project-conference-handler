package com.FMCSULconferencehandler.service;

import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.repositories.ParticipantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.FMCSULconferencehandler.controller.sha.Hashes.hashSHA512;

@Service
@Transactional
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
            // emailService.sendAccountCreationMessage(user);   // Send an e-mail to every newly created user before their password is hashed
            user.setPassword(hashSHA512(user.getPassword()));
            participantRepository.save(user);
        }

    }

    public HashMap<String,Object> getParticipant(UUID id){
        Participant p=participantRepository.findById(id).orElseThrow(() -> new RuntimeException("participant not found"));
        HashMap<String,Object> participant=new HashMap<>();
        participant.put("id",p.getId());
        participant.put("name",p.getName());
        participant.put("surname",p.getSurname());
        participant.put("affilation",p.getAffilation());
        participant.put("email",p.getEmail());



        return  participant;
    }
    public Participant getUserByEmail(String email)
    {
        for(Participant user:users)
        {
            if (user.getEmail().equals(email)) {
                return user;
            }

        }
        return null;
    }

    public  List<Participant> getParticipants()
    {
        users=participantRepository.findAll();
        return users;
    }
}
//DO DOCUMENTATION
//JSON DO TABLICY