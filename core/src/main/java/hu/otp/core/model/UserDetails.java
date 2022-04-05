package hu.otp.core.model;

import hu.otp.core.dao.UserBankCard;
import hu.otp.core.dao.UserDevice;
import hu.otp.core.dao.Users;
import lombok.Data;

import java.util.List;

@Data
public class UserDetails {

    private String token;
    private Users users;
    private UserBankCard userBankCard;
    private List<UserDevice> userDevices;

}
