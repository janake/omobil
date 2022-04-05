package hu.otp.core.repository;

import hu.otp.core.dao.UserToken;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenRepository extends CrudRepository<UserToken, String> {
}
