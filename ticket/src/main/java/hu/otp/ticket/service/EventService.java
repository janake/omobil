package hu.otp.ticket.service;

import hu.otp.ticket.exception.RestException;
import hu.otp.ticket.model.*;
import hu.otp.ticket.model.core.UserBankCard;
import hu.otp.ticket.model.core.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Collections;

@Service
public class EventService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<EventsResponse> getAllEvents() {
        try {
            HttpEntity header = httpEntityForPartnetAuth();
            String url = "http://localhost:58080/getevents";
            ResponseEntity<EventsResponse> response = restTemplateForPartner().exchange(url, HttpMethod.GET, header, EventsResponse.class, 0);
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException httpClientErrorException) {
            return null;
        }
    }

    public ResponseEntity<ResponseSeats> getEvent(Long id) {
        String url = String.format("http://localhost:58080/getevent/%s", id);
        HttpEntity request = httpEntityForPartnetAuth();
        ResponseEntity<ResponseSeats> response = restTemplateForPartner().exchange(
                url, HttpMethod.GET, request, ResponseSeats.class, 0);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    public ResponseEntity<ResponseReservation> pay(Principal principal, Reservation reservation) {
        Users user = getUserFromPrincipal(principal);
        UserBankCard userBankCard = getBankCard(reservation, user);
        validateEventDetails(reservation);
        validateCredit(reservation, userBankCard);
        return reserve(reservation);
    }

    private ResponseEntity<ResponseReservation> reserve(Reservation reservation) {
        String url = "http://localhost:58080/reserve";
        HttpHeaders httpHeaders = httpHeadersForPartnerAuth();
//        httpHeaders.add("CLIENTHASH", "OTPxxxxxxxxxxxxxxxxx");
        ReservationRequest reservationRequest = new ReservationRequest(reservation.getEventId(), reservation.getSeatId());
        HttpEntity<ReservationRequest> request = new HttpEntity<>(reservationRequest, httpHeaders);
        try {
            return restTemplateForPartner().postForEntity(url, request, ResponseReservation.class, 0);
        } catch(Exception e) {
            throw new RestException(e);
        }
    }

    private RestTemplate restTemplateForPartner() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            return restTemplateBuilder
                    .build();
    }

    private void validateCredit(Reservation reservation, UserBankCard userBankCard) {
        ResponseEntity<ResponseSeats> event = getEvent(reservation.getEventId());
        SeatResponse seatResponse = event.getBody().getData().getSeatResponses().stream().filter(
                s -> reservation.getSeatId().equals(s.getId()))
                .findFirst().get();
        validateAmount(userBankCard, seatResponse);
    }

    private void validateAmount(UserBankCard userBankCard, SeatResponse seatResponse) {
        if (seatResponse.getPrice() >= userBankCard.getAmount()) {
            throw new RestException(
                    "A beérkezett kérésben szereplő bankkártyán nem áll rendelkezésre a megfelelő összeg",
                    "A felhasználónak nincs elegendő pénze hogy megvásárolja a jegyet!",
                    "Hibakód: 10101");
        }
    }

    private void validateEventDetails(Reservation reservation) {
        String url = String.format("http://localhost:58080/isseizable/%s/%s", reservation.getEventId(), reservation.getSeatId());
        try {
            restTemplateForPartner().getForObject(url, Boolean.class);
        } catch (ResourceAccessException ex) {
          throw new RestException(
              "A partner rendszer nem elérhető",
              "A külső rendszer nem elérhető!",
              "20404");
        } catch (Exception e) {
            throw new RestException(e);
        }
    }

    private UserBankCard getBankCard(Reservation reservation, Users user) {
        String url = String.format("http://CORE-MS/getbankcard/%s", reservation.getCardId());
        UserBankCard bankCard;
        try{
            bankCard = restTemplate.getForObject(url, UserBankCard.class);
            bankCardExistValidator(reservation.getCardId(), bankCard);
            bankCardUserValidator(user, bankCard);
        } catch (Exception e) {
            throw new RestException(e);
        }
        return bankCard;
    }

    private void bankCardUserValidator(Users user, UserBankCard userBankCardEntity) {
        Users storedUser = userBankCardEntity.getUsers();
        if (!storedUser.getUserId().equals(user.getUserId())) {
            throw new RestException(
                    "A beérkezett kérésben szereplő bankkártya nem az adott felhasználóhoz tartozik",
                    "Ez a bankkártya nem ehhez a felhasználóhoz tartozik",
                    "10100");
        }
    }

    private void bankCardExistValidator(Long cardId, UserBankCard userBankCardEntity) {
        if (!userBankCardEntity.getCardNumber().equals(cardId.toString())) {
            throw new RestException(
                "A beérkezett kérésben szereplő bankkártya nem létezik",
                "A beérkezett kérésben szereplő bankkártya nem létezik",
                "21404"
            );
        }
    }

    private Users getUserFromPrincipal(Principal principal) {
        return (Users)((ResponseEntity)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getBody();
    }

    private HttpEntity httpEntityForPartnetAuth() {
        HttpHeaders headers = httpHeadersForPartnerAuth();
        return new HttpEntity(headers);
    }

    private HttpHeaders httpHeadersForPartnerAuth() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("CLIENTHASH", "OTPxxxxxxxxxxxxxxxxx");
        return headers;
    }

}
