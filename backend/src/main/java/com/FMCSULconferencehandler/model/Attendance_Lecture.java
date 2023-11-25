package com.FMCSULconferencehandler.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Attendance_Lecture {
    @Id
    @GeneratedValue
    private UUID id;

  @ManyToOne
    @JoinColumn(name="lecture_fk")
    private Lecture lecture;


    @ManyToOne
    @JoinColumn(name="participant_fk")
    private Participant participant;

    public Attendance_Lecture(Lecture lecture, Participant participant) {
        this.lecture = lecture;
        this.participant = participant;
    }
}
