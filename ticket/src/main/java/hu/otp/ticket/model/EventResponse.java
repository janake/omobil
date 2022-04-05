package hu.otp.ticket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResponse {

    private Long eventId;
    private String title;
    private String location;
    private String startTimeStamp;
    private String endTimeStamp;

}
