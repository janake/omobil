package hu.otp.ticket.web;

import hu.otp.ticket.model.Reservation;
import hu.otp.ticket.model.EventsResponse;
import hu.otp.ticket.model.ResponseReservation;
import hu.otp.ticket.model.ResponseSeats;
import hu.otp.ticket.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController()
public class Controller {

    @Autowired
    private EventService eventService;

    @GetMapping("/getallevents")
    public ResponseEntity<EventsResponse> getEvents() {
        log.info("getallevents reqest ...");
        ResponseEntity<EventsResponse> allEvents = eventService.getAllEvents();
        return allEvents;
    }

    @GetMapping("/getevent/{id}")
    public ResponseEntity<ResponseSeats> getEvent(@PathVariable Long id) {
        log.info("getevent request ...");
        ResponseEntity<ResponseSeats> event = eventService.getEvent(id);
        return event;
    }

    @PostMapping("/pay")
    public ResponseEntity<ResponseReservation> pay(Principal principal, @RequestBody Reservation reservation) {
        log.info("pay request....");
        return eventService.pay(principal, reservation);
    }

}
