package com.FMCSULconferencehandler.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.util.UUID;




@Entity(name="participant")
@NoArgsConstructor
public class Participant {


    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String surname;
    private String email_login;
    private String affilation;
    private String password;

/*    public Set<Attendance_Event> getAttendence_event() {
        return attendence_event;
    }

    @OneToMany(mappedBy = "participant")
    private Set<Attendance_Event> attendence_event;*/


    public Participant( String name, String surname, String email_login, String affilation, String password) {

        this.name = name;
        this.surname = surname;
        this.email_login = email_login;
        this.affilation = affilation;
        this.password = password;
    }
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail_login() {
        return email_login;
    }

    public void setEmail_login(String email_login) {
        this.email_login = email_login;
    }

    public String getAffilation() {
        return affilation;
    }

    public void setAffilation(String affilation) {
        this.affilation = affilation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
