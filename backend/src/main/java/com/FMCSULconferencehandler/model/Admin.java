package com.FMCSULconferencehandler.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name="admin")
@NoArgsConstructor
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue
    @Schema(hidden = true)
    private UUID id;
    private String login;
    private String pass;

    public Admin(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }
}
