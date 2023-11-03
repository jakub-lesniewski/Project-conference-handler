package com.FMCSULconferencehandler.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class User {
    private String name;
    private String surname;
    private String email_login;
    private String affilation;
    private String password;
    private int permissionLevel;


}
