package hu.otp.ticket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SeatReservationsResponseData {

    private Long eventId;

    @JsonProperty(value = "seats")
    private List<SeatResponse> seatResponses;

}
