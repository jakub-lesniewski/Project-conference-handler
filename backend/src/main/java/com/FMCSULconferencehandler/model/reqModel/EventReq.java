package com.FMCSULconferencehandler.model.reqModel;

import com.FMCSULconferencehandler.model.Event;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventReq {

    private String id;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateStart;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateEnd;
    private String name;

    public Event getEvent()
    {
        return new Event(dateStart,dateEnd,name);
    }
}
