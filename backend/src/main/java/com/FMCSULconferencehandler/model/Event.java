package com.FMCSULconferencehandler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Event {


    @Id
    @GeneratedValue
    private UUID id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime time_start;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime time_end;
    private String name;
    private int amount_of_participants;

    //@ManyToOne
    //@JoinColumn(name = "session_fk")
    private UUID session_fk;

//    @ManyToOne
//    @JoinColumn(name="type_fk")
//    private Type type_fk;

   /* @OneToMany(mappedBy = "event")
    Set<Attendance_Event> event_fk;*/


    public Event(UUID id, LocalDateTime time_start, LocalDateTime time_end, String name ){
        this.id = id;
        this.time_start = time_start;
        this.time_end = time_end;
        this.name = name;
        this.amount_of_participants = 0;

    }
    public Event( LocalDateTime time_start, LocalDateTime time_end, String name,UUID session ){

        this.time_start = time_start;
        this.time_end = time_end;
        this.name = name;
        this.amount_of_participants = 0;
        this.session_fk=session;

    }
}
