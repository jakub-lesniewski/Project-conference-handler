package com.FMCSULconferencehandler.model.reqModel;

import com.FMCSULconferencehandler.model.Participant;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ParticipantReq {
    private String name;
    private String surname;
    private String email;
    private String affiliation;

    public ParticipantReq(String name, String surname, String email, String affiliation) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.affiliation = affiliation;
    }


    public Participant getAttendees (){
        return new Participant(name,surname,email,affiliation);
    }


}
