package hu.otp.core.repository;

import hu.otp.core.dao.UserBankCard;
import hu.otp.core.dao.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserBankCardRepository extends CrudRepository<UserBankCard, String> {

    @Query("SELECT bc FROM UserBankCard bc WHERE bc.users=:users")
    UserBankCard findByUserId(Users users);
}
