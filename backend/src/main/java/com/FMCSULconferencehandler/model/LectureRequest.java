package com.FMCSULconferencehandler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Setter
public class LectureRequest {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime time_start;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime time_end;
    private String name;
    private UUID session_fk;
    private String topic;
    private String Abstract;
    List<UUID> idSpeakers;

    public LectureRequest(LocalDateTime time_start, LocalDateTime time_end, String name, UUID session_fk, String topic, String Abstract,UUID... speakers) {
        this.time_start = time_start;
        this.time_end = time_end;
        this.name = name;
        this.session_fk = session_fk;
        this.topic = topic;
        this.Abstract = Abstract;
        this.idSpeakers=List.of(speakers);
    }
}

