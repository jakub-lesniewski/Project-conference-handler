package com.FMCSULconferencehandler.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity(name = "participant")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Participant {


    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String affiliation;
    private String password;

/*    public Set<Attendance_Event> getAttendence_event() {
        return attendence_event;
    }

    @OneToMany(mappedBy = "participant")
    private Set<Attendance_Event> attendence_event;*/

}
