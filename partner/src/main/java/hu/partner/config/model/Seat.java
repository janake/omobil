package hu.partner.config.model;

import lombok.Getter;

@Getter
public class Seat {
    public String id;
    public Long price;
    public String currency;
    public boolean reserved;
}
