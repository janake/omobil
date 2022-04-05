package hu.partner.service;

import hu.partner.dao.EventDao;
import hu.partner.dao.ReservationDao;
import hu.partner.dao.SeatDao;
import hu.partner.dao.SeatIdDao;
import hu.partner.domain.*;
import hu.partner.exception.RestException;
import hu.partner.repository.EventRepository;
import hu.partner.repository.ReservationRepository;
import hu.partner.repository.SeatRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    ModelMapper modelMapper = new ModelMapper();

    public EventsResponse getEvents() {
        List<EventResponse> eventResponses = fetchEventsFromDB();
        return setEventsResponse(eventResponses);
    }

    public ResponseSeats getEvent(Long id) {
        List<SeatDao> byEventId = seatRepository.findByEventId(id);
        List<SeatResponse> seatResponses = getSeatResponses(byEventId);
        SeatReservationsResponseData seatReservationsResponseData = setSeatsForResponse(id, seatResponses);
        return setResponse(seatReservationsResponseData);
    }

    @Transactional
    public ResponseReservation reserve(Long eventId, String id) {
        ResponseReservation responseReservation = new ResponseReservation();
        Optional<SeatDao> seatByIds = seatRepository.findById(new SeatIdDao(eventId, id));
        SeatDao seatDao = seatByIds.get();
        validateSeat(seatByIds);
        validateReserved(seatDao);
        createReservation(eventId, id, responseReservation, seatDao);
        return responseReservation;
    }

    private List<EventResponse> fetchEventsFromDB() {
        List<EventDao> eventDaos = Lists.from(eventRepository.findAll().iterator());
        List<EventResponse> eventResponses = modelMapper.map(eventDaos, new TypeToken<List<EventDao>>() {}.getType());
        return eventResponses;
    }

    private EventsResponse setEventsResponse(List<EventResponse> eventResponses) {
        EventsResponse eventsResponse = new EventsResponse();
        eventsResponse.setData(eventResponses);
        eventsResponse.setSuccess(true);
        return eventsResponse;
    }

    private void validateReserved(SeatDao seatDao) {
        if (seatDao.isReserved()) {
            throw new RestException("foglalt", "Ez a szék már foglalt", "20010");
        }
    }

    private void validateSeat(Optional<SeatDao> seatByIds) {
        if (!seatByIds.isPresent()) {
            throw new RestException(
                    "Nem létezik ilyen szék",
                    "Nem létezik ilyen szék!",
                    "90002"
            );
        }
    }

    private void createReservation(Long eventId, String id, ResponseReservation responseReservation, SeatDao seatDao) {
        seatDao.setReserved(true);
        seatRepository.save(seatDao);
        ReservationDao reservationResponse = createReservation(eventId, id);
        responseReservation.setSuccess(true);
        responseReservation.setReservationId(reservationResponse.getReservationId());
    }

    private ResponseSeats setResponse(SeatReservationsResponseData seatReservationsResponseData) {
        ResponseSeats response = new ResponseSeats();
        response.setData(seatReservationsResponseData);
        response.setSuccess(true);
        return response;
    }

    private SeatReservationsResponseData setSeatsForResponse(Long id, List<SeatResponse> seatResponses) {
        SeatReservationsResponseData seatReservationsResponseData = new SeatReservationsResponseData();
        seatReservationsResponseData.setSeatResponses(seatResponses);
        seatReservationsResponseData.setEventId(id);
        return seatReservationsResponseData;
    }

    private List<SeatResponse> getSeatResponses(List<SeatDao> byEventId) {
        List<SeatResponse> seatResponses = new ArrayList<>();
        byEventId.forEach(e -> {
            SeatResponse seatResponse = new SeatResponse();
            seatResponse.setId(e.getId().getId());
            seatResponse.setReserved(e.isReserved());
            seatResponse.setCurrency(e.getCurrency());
            seatResponse.setPrice(e.getPrice());
            seatResponses.add(seatResponse);
        });
        return seatResponses;
    }

    private ReservationDao createReservation(Long eventId, String id) {
        ReservationDao reservationDao = new ReservationDao();
        reservationDao.setSeatId(id);
        reservationDao.setEventId(eventId);
        ReservationDao reservationResponse = reservationRepository.save(reservationDao);
        return reservationResponse;
    }

    public ResponseEntity<Boolean> isSeizable(Long eventId, String seatId) {
        Optional<EventDao> eventFromDB = eventRepository.findById(eventId);
        validateEvent(eventFromDB);
        validateStartTime(eventFromDB);
        validateSeat(eventId, seatId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private void validateSeat(Long eventId, String seatId) {
        SeatIdDao seatIdDao = new SeatIdDao(eventId, seatId);
        if (!seatRepository.findById(seatIdDao).isPresent()) {
            throw new RestException(
                    "Nem létezik ilyen szék!",
                    "Nem létezik ilyen szék!",
                    "90002"
            );
        }
    }

    private void validateStartTime(Optional<EventDao> eventFromDB) {
        String startTimeStamp = eventFromDB.get().getStartTimeStamp();
        LocalDateTime eventDateTime =
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(startTimeStamp)), ZoneId.systemDefault());
        if (eventDateTime.isBefore(LocalDateTime.now())) {
            throw new RestException(
                "Az esemény már elkezdődött",
                "Az esemény már elkezdődött",
                "20011");
        }
    }

    private void validateEvent(Optional<EventDao> eventFromDB) {
        if (!eventFromDB.isPresent()) {
            throw new RestException(
                    "Nem létezik ilyen esemény!",
                    "Nem létezik ilyen esemény!",
                    "90001");
        }
    }
}
