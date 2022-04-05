package hu.partner.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class EventDao {

    @Id()
    private Long eventId;
    private String title;
    private String location;
    private String startTimeStamp;
    private String endTimeStamp;

}
