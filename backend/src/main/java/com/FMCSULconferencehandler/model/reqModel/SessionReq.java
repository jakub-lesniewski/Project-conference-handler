package com.FMCSULconferencehandler.model.reqModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SessionReq {
    private String name;
    private String building;
    private int atendeeLimit;
    private String city;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startingDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endingDate;
    private String room;
    private String street;
    private LectureRequest[] eventsArr;
}
