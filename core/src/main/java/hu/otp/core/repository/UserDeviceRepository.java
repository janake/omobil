package hu.otp.core.repository;

import hu.otp.core.dao.UserDevice;
import hu.otp.core.dao.UserDeviceId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDeviceRepository extends CrudRepository<UserDevice, UserDeviceId> {

    @Query("SELECT u FROM UserDevice u WHERE u.userDeviceId.userId=:id")
    List<UserDevice> getUserDevicesByUserId(@Param("id") String userId);
}
