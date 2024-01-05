package com.FMCSULconferencehandler.service;

import com.FMCSULconferencehandler.model.Admin;
import com.FMCSULconferencehandler.model.Participant;
import com.FMCSULconferencehandler.model.Session;
import com.FMCSULconferencehandler.repositories.AdminRepository;
import com.FMCSULconferencehandler.repositories.ParticipantRepository;
import com.FMCSULconferencehandler.repositories.TitleRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.FMCSULconferencehandler.controller.sha.Hashes.hashSHA512;

@Service
public class AdminService {
    private AdminRepository adminRepository;
    private ParticipantRepository participantRepository;

    public AdminService(AdminRepository adminRepository, ParticipantRepository participantRepository){
        this.adminRepository = adminRepository;
        this.participantRepository = participantRepository;
    }

    public boolean saveAdmin(Admin reqAdmin) {
        if(!Pattern.compile("\\S{4,}")
                .matcher(reqAdmin.getLogin())
                .matches())
            return false;

        Optional<Admin> admin = adminRepository.findByLogin(reqAdmin.getLogin());
        if(!admin.isPresent()){
            reqAdmin.setPass(hashSHA512(reqAdmin.getPass()));
            adminRepository.save(reqAdmin);

            return true;
        }
        return false;
    }

    public Optional<Admin> findByLogin(String login){
        return adminRepository.findByLogin(login);
    }

    public String passwordGenerate()
    {
        String password="";
        Random random=new Random();
        for(int i=0;i<8;i++)
        {
            password=password+((random.nextInt(10)));
        }
        return password;
    }
    public boolean checkName(String name){
        if(name == null || name.isEmpty())
            return false;
        if(name.charAt(0) < 65 || name.charAt(0) > 90)
            return false;
        for(int i = 1; i < name.length(); i++)
            if(name.charAt(i) < 97 || name.charAt(i) > 122)
                return false;
        return true;
    }

    public boolean checkMail(String mail){
        return Pattern.compile("^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")
                .matcher(mail)
                .matches();
    }

    public void updateParticipant(Participant participant){
        Participant updateParticipant = participantRepository.findById(participant.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Participant not found" , 1));

        if(!(checkMail(participant.getEmail()) && checkName(participant.getName()) &&
                checkName(participant.getSurname()) && participant.getPassword() != null && participant.getPassword().length() >= 4))
            throw new CustomDataIntegrityViolationException("New data is invalid");

        updateParticipant.setName(participant.getName());
        updateParticipant.setSurname(participant.getSurname());
        updateParticipant.setEmail(participant.getEmail());
        updateParticipant.setPassword(hashSHA512(participant.getPassword()));
        updateParticipant.setAffiliation(participant.getAffiliation());

        participantRepository.save(updateParticipant);
    }
}
