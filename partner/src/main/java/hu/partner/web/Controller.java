package hu.partner.web;

import hu.partner.domain.EventsResponse;
import hu.partner.domain.ReservationRequest;
import hu.partner.domain.ResponseReservation;
import hu.partner.domain.ResponseSeats;
import hu.partner.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Value("${identifier}")
    private String identifier;

    @Autowired
    private EventService eventService;

    @GetMapping("/getevents")
    public ResponseEntity<EventsResponse> getEvents() {
        EventsResponse eventsResponse = eventService.getEvents();
        return new ResponseEntity<>(eventsResponse, HttpStatus.OK);
    }

    @GetMapping("/getevent/{id}")
    public ResponseEntity<ResponseSeats> getEvent(@PathVariable Long id) {
        ResponseSeats event = eventService.getEvent(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/reserve/{eventId}/{seatId}")
    public ResponseEntity<ResponseReservation> PostReserve(@RequestHeader HttpHeaders headers, @PathVariable Long eventId, @PathVariable String seatId) {
        if (validateHeader(headers)) {
            ResponseReservation responseReservation = eventService.reserve(eventId, seatId);
            return new ResponseEntity<>(responseReservation, HttpStatus.CREATED);
        } else {
            ResponseReservation responseReservation = new ResponseReservation();
            responseReservation.setSuccess(false);
            return new ResponseEntity<>(responseReservation, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<ResponseReservation> GetReserve(@RequestHeader HttpHeaders headers, @RequestBody ReservationRequest reservationRequest) {
        if (validateHeader(headers)) {
            ResponseReservation responseReservation = eventService.reserve(reservationRequest.getEventId(), reservationRequest.getSeatId());
            return new ResponseEntity<>(responseReservation, HttpStatus.CREATED);
        } else {
            ResponseReservation responseReservation = new ResponseReservation();
            responseReservation.setSuccess(false);
            return new ResponseEntity<>(responseReservation, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/isseizable/{eventId}/{seatId}")
    public ResponseEntity<Boolean> isSeizable(@PathVariable Long eventId, @PathVariable String seatId) {
        return eventService.isSeizable(eventId, seatId);
    }

    private boolean validateHeader(HttpHeaders headers) {
        return headers != null &&
                headers.get("clienthash") != null &&
                headers.get("clienthash").get(0).equals(identifier);
    }
}
