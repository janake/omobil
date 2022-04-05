package hu.otp.ticket.model.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDeviceId {

    private String userId;
    private String deviceHash;


}
