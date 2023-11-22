package com.FMCSULconferencehandler.model.conference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Lecture{

    @Id
    @GeneratedValue
    private UUID id;
    private String topic;
    @Column(name="abstract")
    private String Abstract;

    @OneToOne
    @JoinColumn(name = "event_fk", referencedColumnName = "id")
    private Event event;



    public Lecture( String topic, String Abstract,Event event) {

        this.topic = topic;
        this.Abstract = Abstract;
        this.event = event;

    }


    public HashMap<String,Object> jsonLong() {
       HashMap<String,Object> json=new HashMap<>();
       json.put("id",id.toString());
       json.put("name", event.getName());
       json.put("topic",topic);
        json.put("time-start",event.getTime_start().toString());
        json.put("time-end",event.getTime_end().toString());
        json.put("amount_of_participants",String.valueOf(event.getAmount_of_participants()));
        json.put("session_fk",event.getSession_fk().toString());
        json.put("abstract",getAbstract());

    return json;
    }
}
