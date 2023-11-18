package com.FMCSULconferencehandler.model.conference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Type {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String abbrevation;

    public Type(UUID id, String name, String abbrevation) {
        this.id = id;
        this.name = name;
        this.abbrevation = abbrevation;
    }



}


