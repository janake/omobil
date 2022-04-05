package hu.partner.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRequest {

    Long eventId;
    String seatId;

}
