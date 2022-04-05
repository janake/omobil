package hu.partner.config.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Events {
    public List<Event> data;
    public boolean success;
}
