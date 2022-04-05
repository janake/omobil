package hu.partner.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class EventResponse {

    private Long eventId;
    private String title;
    private String location;
    private LocalTime startTimeStamp;
    private LocalTime endTimeStamp;

}
