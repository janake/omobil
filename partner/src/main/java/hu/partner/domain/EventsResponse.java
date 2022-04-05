package hu.partner.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventsResponse {

    private List<EventResponse> data;
    private boolean success;

}
