package com.FMCSULconferencehandler.model.conference;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@NoArgsConstructor
@Setter
@Getter
public class Session {
    @Id
    @GeneratedValue()
    private UUID id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime time_start;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime time_end;
    private String city;
    private String street;
    private String building;
    private String room_number;





    public Session(String name, LocalDateTime time_start, LocalDateTime time_end, String city, String street, String building, String room_number) {
        this.name = name;
        this.time_start = time_start;
        this.time_end = time_end;
        this.city = city;
        this.street = street;
        this.building = building;
        this.room_number = room_number;
    }
}
