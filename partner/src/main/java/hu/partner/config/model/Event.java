package hu.partner.config.model;

import lombok.Getter;

@Getter
public class Event {
    public Long eventId;
    public String title;
    public String location;
    public String startTimeStamp;
    public String endTimeStamp;
}
