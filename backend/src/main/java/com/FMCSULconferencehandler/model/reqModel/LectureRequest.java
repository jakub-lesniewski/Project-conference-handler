package com.FMCSULconferencehandler.model.reqModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class LectureRequest {
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startingDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endingDate;
    private String name;
    private String _abstract;
    private UUID session_fk;
//    private String topic;

    List<String> lecturers = new LinkedList<>();

    public LectureRequest(LocalDateTime time_start, LocalDateTime time_end, String name, UUID session_fk, String Abstract,String... speakers) {
        this.startingDate = time_start;
        this.endingDate = time_end;
        this.name = name;
        this.session_fk = session_fk;
        this._abstract = Abstract;
        this.lecturers=List.of(speakers);
    }
}

