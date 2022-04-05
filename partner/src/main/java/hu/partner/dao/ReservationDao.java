package hu.partner.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ReservationDao {

    @Id()
    @GeneratedValue()
    private Long reservationId;
    private Long eventId;
    private String seatId;

}
