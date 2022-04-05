package hu.otp.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reservation {

    Long eventId;
    String seatId;
    Long cardId;

}
