package hu.partner.repository;

import hu.partner.dao.ReservationDao;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<ReservationDao, Long> {
}
