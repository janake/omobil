package hu.partner.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ResponseReservation {

    @Id
    @GeneratedValue
    private Long reservationId;
    private Boolean success;

}
