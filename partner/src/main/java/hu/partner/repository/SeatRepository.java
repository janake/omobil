package hu.partner.repository;

import hu.partner.dao.SeatDao;
import hu.partner.dao.SeatIdDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends CrudRepository<SeatDao, SeatIdDao> {

    @Query("SELECT s FROM SeatDao s WHERE s.id.eventId=:id")
    List<SeatDao> findByEventId(@Param("id") long eventId);
}
