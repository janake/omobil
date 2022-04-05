package hu.otp.ticket.model.core;

import lombok.Data;

import java.util.List;

@Data
public class UserDetails {

    private String token;
    private Users users;
    private UserBankCard userBankCard;
    private List<UserDevice> userDevices;

}
