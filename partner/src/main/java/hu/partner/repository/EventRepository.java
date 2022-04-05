package hu.partner.repository;

import hu.partner.dao.EventDao;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventDao, Long> {
}
