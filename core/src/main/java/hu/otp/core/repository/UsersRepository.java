package hu.otp.core.repository;

import hu.otp.core.dao.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, String> {}
