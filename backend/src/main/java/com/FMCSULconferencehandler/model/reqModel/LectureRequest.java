package com.FMCSULconferencehandler.model.reqModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class LectureRequest {
    private UUID id;
    @JsonFormat(pattern="HH:mm")
    private LocalTime timeStart;
    @JsonFormat(pattern="HH:mm")
    private LocalTime timeEnd;
    private String name;
    private String _abstract;
    private UUID session_fk;
    private String type;
//    private String topic;

    private String lecturers;
    //List<String> leadEmail = new LinkedList<>();

    public LectureRequest(LocalTime time_start, LocalTime time_end, String name, UUID session_fk, String Abstract,String speakers) {
        this.timeStart = time_start;
        this.timeEnd = time_end;
        this.name = name;
        this.session_fk = session_fk;
        this._abstract = Abstract;
        this.lecturers=speakers;
    }

    public LectureRequest(UUID id,LocalTime time_start, LocalTime time_end, String name, UUID session_fk, String Abstract,String speakers,String type) {
        this.id = id;
        this.timeStart = time_start;
        this.timeEnd = time_end;
        this.name = name;
        this.session_fk = session_fk;
        this._abstract = Abstract;
        this.lecturers=speakers;
        this.type = type;
    }
}

