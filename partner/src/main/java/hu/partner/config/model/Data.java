package hu.partner.config.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Data {

    private Long eventId;
    private List<Seat> seats;

}
