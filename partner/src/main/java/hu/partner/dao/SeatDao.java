package hu.partner.dao;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class SeatDao {

    @EmbeddedId
    private SeatIdDao id;
    private Long price;
    private String currency;
    private boolean reserved;

}