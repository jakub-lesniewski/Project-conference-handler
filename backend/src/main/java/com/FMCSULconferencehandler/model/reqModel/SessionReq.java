package com.FMCSULconferencehandler.model.reqModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SessionReq {
    private String id;
    private String name;
    private String building;
    private int attendeeLimit;
    private String city;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateStart;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateEnd;
    private String room;
    private String street;
    private LectureRequest[] sessionEventsArr;
}
