package hu.otp.ticket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSeats {
    public SeatReservationsResponseData data;
    public boolean success;
}
