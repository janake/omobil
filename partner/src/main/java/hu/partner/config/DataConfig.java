package hu.partner.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.partner.config.model.Events;
import hu.partner.config.model.Seats;
import hu.partner.dao.EventDao;
import hu.partner.dao.SeatDao;
import hu.partner.dao.SeatIdDao;
import hu.partner.repository.EventRepository;
import hu.partner.repository.SeatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DataConfig {

    private ObjectMapper objectMapper = new ObjectMapper();
    private ModelMapper modelMapper = new ModelMapper();

    public DataConfig(SeatRepository seatRepository, EventRepository eventRepository) throws IOException {

        Events events = readEventsFromJson();
        persistEvents(eventRepository, events);

        Seats seats1 = readSeatsFromJson("samples/getEvent1.json");
        persistSeats(seatRepository, seats1);

        Seats seats2 = readSeatsFromJson("samples/getEvent2.json");
        persistSeats(seatRepository, seats2);

        Seats seats3 = readSeatsFromJson("samples/getEvent3.json");
        persistSeats(seatRepository, seats3);

    }

    private void persistEvents(EventRepository eventRepository, Events events) {
        events.getData().stream().forEach(data -> {
            EventDao map = modelMapper.map(data, EventDao.class);
            eventRepository.save(map);
        });
    }

    private Events readEventsFromJson() throws IOException {
        Events events = objectMapper.readValue(
                getClass().getClassLoader().getResource("samples/getEvents.json"), Events.class
        );
        return events;
    }

    private void persistSeats(SeatRepository seatRepository, Seats seats) {
        seats.getData().getSeats().forEach(seat -> {
            SeatIdDao seatIdDao = new SeatIdDao(seats.getData().getEventId(), seat.getId());
            SeatDao seatDao = new SeatDao();
            seatDao.setId(seatIdDao);
            seatDao.setCurrency(seat.getCurrency());
            seatDao.setPrice(seat.getPrice());
            seatDao.setReserved(seat.isReserved());
            seatRepository.save(seatDao);
        });
    }

    private Seats readSeatsFromJson(String path) throws IOException {
        return objectMapper.readValue(
                getClass().getClassLoader().getResource(path), Seats.class);
    }

}
