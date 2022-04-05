package hu.otp.ticket.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatResponse {

    private String id;
    private Long price;
    private String currency;
    private boolean reserved;

}