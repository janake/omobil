package hu.otp.ticket.model.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDevice implements Serializable {

    private UserDeviceId userDeviceId;

}
